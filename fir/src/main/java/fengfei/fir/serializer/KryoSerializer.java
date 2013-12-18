package fengfei.fir.serializer;

import fengfei.fir.utils.Kryor;

/**
 */
public class KryoSerializer implements ObjectSerializer {
    @Override
    public byte[] write(Object obj) {
        return Kryor.write(obj);
    }

    @Override
    public <T> T read(byte[] bytes, Class<T> clazz) {
        return Kryor.read(bytes, clazz);
    }
}
