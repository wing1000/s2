
package fengfei.ucm.registry;


/**
 */
public interface Invoker<T>  {

    /**
     * get service interface.
     * 
     * @return service interface.
     */
    Class<T> getInterface();

    /**
     * invoke.
     * 
     * @param invocation
     * @return result
     */
    T invoke(Invocation invocation) throws Exception;

}