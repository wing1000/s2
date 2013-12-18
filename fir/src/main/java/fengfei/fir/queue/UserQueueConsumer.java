package fengfei.fir.queue;

import fengfei.fir.search.lucene.UserIndexCreator;
import fengfei.fir.utils.PausableLock;
import fengfei.ucm.entity.profile.UserPwd;

/**
 */
public class UserQueueConsumer extends BaseConsumer implements Runnable {
    private UserIndexCreator creator;

    public UserQueueConsumer(QueueService queueService,
                             PausableLock pausableLock,
                             UserIndexCreator creator) {
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
        if (message.getData() instanceof UserPwd) {
            UserPwd user = (UserPwd) message.getData();
            switch (message.getOperationType()) {
                case Add:
                    creator.add(user);
                    break;
                case Update:
                    creator.updateByID(user);
                    break;
                case Delete:
                    creator.delete(String.valueOf(user.idUser));
                    break;
            }

        }
    }


}
