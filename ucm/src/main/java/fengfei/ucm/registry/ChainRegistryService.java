package fengfei.ucm.registry;

public interface ChainRegistryService<T> {

    void register(T object, ChainExecuteType type);

    void register(int index, T object, ChainExecuteType type);
}