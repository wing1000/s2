package fengfei.fir.serializer;

/**
 */
public interface ObjectSerializer {
    byte[] write(Object obj);
    <T> T read(byte[] bytes,Class<T> clazz);
}
