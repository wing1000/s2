package fengfei.fir.queue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class QueueMessage<T> implements Serializable {
    public MessageType messageType;
    public OperationType operationType;
    public T data;

    public QueueMessage(MessageType messageType, OperationType operationType, T data) {
        this.messageType = messageType;
        this.operationType = operationType;
        this.data = data;
    }

    public QueueMessage() {
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueueMessage that = (QueueMessage) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (messageType != that.messageType) return false;
        if (operationType != that.operationType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageType != null ? messageType.hashCode() : 0;
        result = 31 * result + (operationType != null ? operationType.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QueueMessage{" +
                "messageType=" + messageType +
                ", operationType=" + operationType +
                ", data=" + data +
                '}';
    }

    public static enum OperationType {
        Add,
        Update,
        Delete
    }

    public static enum MessageType {
        User(0),
        Photo(1),
        Story(2);

        private final int value;
        private static Map<Integer, MessageType> cache = new HashMap<Integer, MessageType>();

        static {
            for (MessageType type : values()) {
                cache.put(type.value, type);
            }
        }

        private MessageType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static MessageType valueOf(int value) {
            return cache.get(value);
        }

        public static MessageType find(String name) {
            if (name == null || "".equals(name)) {
                return null;
            }
            MessageType[] fs = values();
            for (MessageType enumType : fs) {
                if (enumType.name().equalsIgnoreCase(name)) {
                    return enumType;
                }

            }
            throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + name);
        }
    }
}
