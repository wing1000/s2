package fengfei.web.app.init;

import fengfei.fir.queue.*;
import fengfei.fir.search.lucene.LuceneFactory;
import fengfei.fir.search.lucene.PhotoIndexCreator;
import fengfei.fir.search.lucene.Searcher;
import fengfei.fir.search.lucene.UserIndexCreator;
import fengfei.fir.utils.PausableLock;
import fengfei.forest.slice.utils.ResourcesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SearchPlugin extends BaseInit {

    static Logger logger = LoggerFactory.getLogger(SearchPlugin.class);

    public void onApplicationStart() throws IOException {
        Properties p = init();
        String photoQueuePath = p.getProperty("fqueue.path.photo");
        String lucenePhotoPath = p.getProperty("lucene.index.path.photo");

        String userConsumerSizeStr = p.getProperty("queue.user.consumer.size");
        String photoConsumerSizeStr = p.getProperty("queue.photo.consumer.size");
        String keepAliveTimeStr = p.getProperty("queue.consumer.keepAliveTime");
        String incrementStr = p.getProperty("queue.consumer.max.increment");
        int userConsumerSize = Integer.parseInt(userConsumerSizeStr);
        int photoConsumerSize = Integer.parseInt(photoConsumerSizeStr);
        float increment = Float.parseFloat(incrementStr);
        int corePoolSize = userConsumerSize + photoConsumerSize;
        int maximumPoolSize = corePoolSize * new Float(1 + increment).intValue();
        long keepAliveTime = Long.parseLong(keepAliveTimeStr);
        logger.info("starting queue consume ThreadPoolExecutor...");
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                corePoolSize,// corePoolSize
                maximumPoolSize,// maximumPoolSize
                keepAliveTime,// keepAliveTime
                TimeUnit.MILLISECONDS,// TimeUnit
                new SynchronousQueue<Runnable>());

        String photoQueueName = "queue_photo";
        QueueService photoQueueService = null;
        try {
            photoQueueService = new QueueServiceFQueueImpl(photoQueueName, photoQueuePath);
            photoQueueService.start();
        } catch (Exception e) {
            logger.error("Photo queue service start error.", e);

        }
        PausableLock photoPausableLock = new PausableLock();
        PhotoQueue.queueProducer = new QueueProducer(photoQueueService, photoPausableLock);
        logger.info("Initialized PhotoQueue.queueProducer.");
        //lucene
        LuceneFactory photoLuceneFactory = LuceneFactory.get(lucenePhotoPath);
        PhotoIndexCreator photoIndexCreator = new PhotoIndexCreator(photoLuceneFactory);
        PhotoQueueConsumer photoQueueConsumer = new PhotoQueueConsumer(photoQueueService,
                                                                       photoPausableLock,
                                                                       photoIndexCreator);
        logger.info("starting photoQueueConsumer.");
        for (int i = 0; i < photoConsumerSize; i++) {
            poolExecutor.execute(photoQueueConsumer);
        }
        //user queue consume and index create.
        String userQueuePath = p.getProperty("fqueue.path.user");
        String luceneUserPath = p.getProperty("lucene.index.path.user");

        String userQueueName = "queue_user";
        QueueService userQueueService = null;
        try {
            userQueueService = new QueueServiceFQueueImpl(userQueueName, userQueuePath);
            userQueueService.start();
        } catch (Exception e) {
            logger.error("User queue service start error.", e);

        }
        PausableLock userPausableLock = new PausableLock();
        UserQueue.queueProducer = new QueueProducer(userQueueService, userPausableLock);
        logger.info("Initialized UserQueue.queueProducer.");
        //lucene
        LuceneFactory userLuceneFactory = LuceneFactory.get(luceneUserPath);
        UserIndexCreator userIndexCreator = new UserIndexCreator(userLuceneFactory);
        UserQueueConsumer userQueueConsumer = new UserQueueConsumer(userQueueService,
                                                                    userPausableLock,
                                                                    userIndexCreator);
        logger.info("starting userQueueConsumer.");
        for (int i = 0; i < userConsumerSize; i++) {
            poolExecutor.execute(userQueueConsumer);
        }

        //
        logger.info("Init user searcher.");
        Searcher.userSearcher = new Searcher(userLuceneFactory);
        logger.info("Init photo searcher.");
        Searcher.photoSearcher = new Searcher(photoLuceneFactory);
    }


}
