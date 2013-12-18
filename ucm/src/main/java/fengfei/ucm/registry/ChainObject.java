package fengfei.ucm.registry;

public class ChainObject<T> {

    private ChainExecuteType type;
    private T object;

    public ChainObject(ChainExecuteType type, T object) {
        super();
        this.type = type;
        this.object = object;
    }

    public ChainExecuteType getType() {
        return type;
    }

    public void setType(ChainExecuteType type) {
        this.type = type;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
