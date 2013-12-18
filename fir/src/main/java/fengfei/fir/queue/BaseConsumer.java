package fengfei.fir.queue;

import fengfei.fir.utils.PausableLock;

/**
 */
public class BaseConsumer {
    protected int blockTimeUnit = 100;
    protected int minBlockTime = 100;
    protected int maxBlockTime = 2000;
    protected int blockTime = minBlockTime;
    protected PausableLock pausableLock;
    protected QueueService queueService;

    public BaseConsumer(QueueService queueService, PausableLock pausableLock) {
        this.queueService = queueService;
        this.pausableLock = pausableLock;
    }

    public QueueService getQueueService() {
        return queueService;
    }

    public void setQueueService(QueueService queueService) {
        this.queueService = queueService;
    }

    protected void decreaseSleepTime() {
        blockTime = minBlockTime;
    }

    protected void increaseSleepTime() {
        if (blockTime >= maxBlockTime) {
            blockTime = maxBlockTime;
            return;
        }
        blockTime = blockTime + blockTimeUnit;

    }

    public void setMaxBlockTime(int maxBlockTime) {
        this.maxBlockTime = maxBlockTime;
    }

    public void setPausableLock(PausableLock pausableLock) {
        this.pausableLock = pausableLock;
    }

    public PausableLock getPausableLock() {
        return pausableLock;
    }
}
