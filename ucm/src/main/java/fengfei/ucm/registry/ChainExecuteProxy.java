package fengfei.ucm.registry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainExecuteProxy<T> implements ChainRegistryService<T> {

    private static Logger logger = LoggerFactory.getLogger(ChainExecuteProxy.class);
    protected ChainObjectRegistry<T> registry = new ChainObjectRegistry<T>();
    private Map<String, Method> methodMap = new ConcurrentHashMap<String, Method>();

    private long maxTimeMillis = 1000;

    public ChainExecuteProxy() {
        super();
    }

    @Override
    public void register(T object, ChainExecuteType type) {
        registry.register(object, type);
    }

    @Override
    public void register(int index, T object, ChainExecuteType type) {
        registry.register(index, object, type);
    }

    public ChainObjectRegistry<T> getRegistry() {
        return registry;
    }

    private void preReadMethod(Class<T> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        // Method[] methods = clazz.getInterfaces()[0].getDeclaredMethods();
        for (Method method : methods) {
            this.methodMap.put(method.toGenericString(), method);
        }
    }

    @SuppressWarnings("unchecked")
    public T newInstance(Class<T> clazz) {
        preReadMethod(clazz);
        ChainInvoker handler = new ChainInvoker(clazz);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, handler);
    }

    private class ChainInvoker implements InvocationHandler {

        private Class<T> clazz;

        public ChainInvoker(Class<T> clazz) {
            super();
            this.clazz = clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            long start = System.currentTimeMillis();
            try {
                List<ChainObject<T>> eos = registry.getExecuteObjects();
                Object rs = null;
                ArrayList<Object> results = new ArrayList<>();
                for (ChainObject<T> eo : eos) {
                    args[0] = results;
                    Method origin = methodMap.get(method.toGenericString());
                    rs = origin.invoke(clazz, args);
                    results.add(rs);

                    if (eo.getType() == ChainExecuteType.NonReturnValue) {
                        //
                    } else if (eo.getType() == ChainExecuteType.ReturnValue) {
                        if (rs != null) {
                            break;
                        }
                    }
                }

                log(method, args, start, false);
                return rs;
            } catch (Throwable e) {
                log(method, args, start, true);
                logger.error("Can not write to mysql ", e);
                throw e;

            }
        }

        protected void log(Method method, Object[] args, long start, boolean isError) {
            long end = System.currentTimeMillis();
            long time = end - start;
            if (time > maxTimeMillis) {
                logger.info(String.format(
                    "%s : args=%s ,error=%b , time=%d",
                    method.getName(),
                    Arrays.asList(args).toString(),
                    isError,
                    time));
            }
        }
    }

    public static interface RetryCallback {

        void execute() throws Exception;
    }

}
