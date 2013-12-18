package fengfei.ucm.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChainObjectRegistry<T> {

    private List<ChainObject<T>> executeObjects = Collections
        .synchronizedList(new ArrayList<ChainObject<T>>());

    public ChainObjectRegistry() {
    }

    public void register(T object, ChainExecuteType type) {
        ChainObject<T> eo = new ChainObject<T>(type, object);
        executeObjects.add(eo);
    }

    public void register(int index, T object, ChainExecuteType type) {
        ChainObject<T> eo = new ChainObject<T>(type, object);
        executeObjects.add(index - 1, eo);
    }

    public List<ChainObject<T>> getExecuteObjects() {
        return executeObjects;
    }

    public int size() {
        return executeObjects.size();
    }
}
