package fengfei.fir.queue;

import fengfei.fir.utils.PausableLock;

/**
 */
public class QueueProducer {
    private QueueService queueService;
    private PausableLock pausableLock;

    public QueueProducer(QueueService queueService, PausableLock pausableLock) {
        this.queueService = queueService;
        this.pausableLock = pausableLock;
    }

    public <T> void add(QueueMessage<T> item) {
        queueService.add(item);
        pausableLock.resume();
    }
}
