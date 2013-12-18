package fengfei.fir.queue;

import fengfei.ucm.entity.photo.Photo;

/**
 */
public class PhotoQueue {
    public static QueueProducer queueProducer;

    public static void add(Photo photo) {
        QueueMessage<Photo> message = new QueueMessage<>(QueueMessage.MessageType.Photo, QueueMessage.OperationType.Add, photo);
        queueProducer.add(message);
    }

    public static void update(Photo photo) {
        QueueMessage<Photo> message = new QueueMessage<>(QueueMessage.MessageType.Photo, QueueMessage.OperationType.Update, photo);
        queueProducer.add(message);
    }

    public static void delete(Photo photo) {
        QueueMessage<Photo> message = new QueueMessage<>(QueueMessage.MessageType.Photo, QueueMessage.OperationType.Delete, photo);
        queueProducer.add(message);
    }
}
