package fengfei.ucm.entity.photo;

import java.util.HashMap;
import java.util.Map;

public enum Canceled {
    No(0), Yes(1);

    private final int value;
    private static Map<Integer, Canceled> cache = new HashMap<Integer, Canceled>();
    static {
        for (Canceled type : values()) {
            cache.put(type.value, type);
        }
    }

    private Canceled(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Canceled valueOf(int value) {
        return cache.get(value);
    }

    public static Canceled find(String name) {
        if (name == null || "".equals(name)) {
            return null;
        }
        Canceled[] fs = values();
        for (Canceled enumType : fs) {
            if (enumType.name().equalsIgnoreCase(name)) {
                return enumType;
            }

        }
        throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + name);
    }
}
