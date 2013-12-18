package fengfei.fir.queue;

import fengfei.fir.search.lucene.PhotoIndexCreator;
import fengfei.fir.utils.PausableLock;
import fengfei.ucm.entity.photo.Photo;

/**
 */
public class PhotoQueueConsumer extends BaseConsumer implements Runnable {
    private PhotoIndexCreator creator;

    public PhotoQueueConsumer(QueueService queueService,
                              PausableLock pausableLock,
                              PhotoIndexCreator creator) {
        super(queueService, pausableLock);
        this.creator = creator;
    }

    @Override
    public void run() {
        pausableLock.setPaused(true);
        while (true) {
            QueueMessage<?> message = queueService.poll();
            try {
                if (message == null) {
                    pausableLock.setPaused(true);
                    increaseSleepTime();
                    pausableLock.pause(blockTime);
                } else {
                    pausableLock.setPaused(false);
                    decreaseSleepTime();
                    process(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void process(QueueMessage<?> message) throws Exception {
        if (message.getData() instanceof Photo) {
            Photo photo = (Photo) message.getData();
            switch (message.getOperationType()) {
                case Add:
                    creator.add(photo);
                    break;
                case Update:
                    creator.updateByID(photo);
                    break;
                case Delete:
                    creator.delete(String.valueOf(photo.idPhoto));
                    break;
            }

        }
    }


}
