package fengfei.ucm.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChainExecuteProxy<T> implements ChainRegistryService<T> {

    private static Logger logger = LoggerFactory.getLogger("Execute_time");
    protected ChainObjectRegistry<T> registry = new ChainObjectRegistry<T>();
    private Map<String, Method> methodMap = new ConcurrentHashMap<String, Method>();
    private Class<T> clazz;
    private long maxTimeMillis = 1000;

    public ChainExecuteProxy(Class<T> clazz) {
        super();
        this.clazz = clazz;
        preReadMethod();
    }

    public static <E> ChainExecuteProxy<E> create(Class<E> clazz) {
        return new ChainExecuteProxy<E>(clazz);
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

    private void preReadMethod() {
        Method[] methods = clazz.getDeclaredMethods();
        // Method[] methods = clazz.getInterfaces()[0].getDeclaredMethods();
        for (Method method : methods) {
            this.methodMap.put(method.toGenericString(), method);
        }
    }

    @SuppressWarnings("unchecked")
    public T newInstance() {

        ChainInvoker handler = new ChainInvoker();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, handler);
    }

    private class ChainInvoker implements InvocationHandler {

        public ChainInvoker() {
            super();
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
                    rs = origin.invoke(eo.getObject(), args);
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
