package fengfei.fir.queue;

import fengfei.ucm.entity.profile.UserPwd;

/**
 */
public class UserQueue {
    public static QueueProducer queueProducer;

    public static void add(UserPwd user) {
        QueueMessage<UserPwd> message = new QueueMessage<>(QueueMessage.MessageType.User, QueueMessage.OperationType.Add, user);
        queueProducer.add(message);
    }

    public static void update(UserPwd user) {
        QueueMessage<UserPwd> message = new QueueMessage<>(QueueMessage.MessageType.User, QueueMessage.OperationType.Update, user);
        queueProducer.add(message);
    }

    public static void delete(UserPwd user) {
        QueueMessage<UserPwd> message = new QueueMessage<>(QueueMessage.MessageType.User, QueueMessage.OperationType.Delete, user);
        queueProducer.add(message);
    }
}
