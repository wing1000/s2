package fengfei.fir.queue;

/**
 */
public interface QueueService {
    void start() throws Exception;

    <T> void add(QueueMessage<T> item);

    <T> QueueMessage<T> poll();

    void close();

}
