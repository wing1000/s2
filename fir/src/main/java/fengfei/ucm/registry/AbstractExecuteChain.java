package fengfei.ucm.registry;

import java.util.List;

public class AbstractExecuteChain<T> implements ChainRegistryService<T> {

    protected ChainObjectRegistry<T> registry = new ChainObjectRegistry<T>();

    public AbstractExecuteChain() {
    }

    public <S> S execute(Callback<S, T> callback) throws Exception {
        List<ChainObject<T>> eos = registry.getExecuteObjects();
        S rs = null;
        for (ChainObject<T> eo : eos) {
            rs = callback.execute(eo);
            if (eo.getType() == ChainExecuteType.NonReturnValue) {
                //
            } else if (eo.getType() == ChainExecuteType.ReturnValue) {
                // String info = i == 0 ? " From cache: " : " From mysql ";
                if (rs != null) {
                    break;
                }
            }
        }
        return rs;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.livejournal.service.relation.service.RegistryService#register(T,
     * com.livejournal.service.relation.service.ExecuteType)
     */
    @Override
    public void register(T object, ChainExecuteType type) {
        registry.register(object, type);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.livejournal.service.relation.service.RegistryService#register(int, T,
     * com.livejournal.service.relation.service.ExecuteType)
     */
    @Override
    public void register(int index, T object, ChainExecuteType type) {
        registry.register(index, object, type);
    }

    public ChainObjectRegistry<T> getRegistry() {
        return registry;
    }

    public static interface Callback<S, T> {

        S execute(ChainObject<T> eo) throws Exception;
    }
}
