package fengfei.ucm.entity.profile;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * #by
 * 4=Attribution License 3.0
 * #by.nd
 * 5=Attribution No Derivatives License 3.0
 * #by.sa
 * 6=Attribution Share Alike License 3.0
 * #by.nc
 * 1=Attribution Non-Commercial License 3.0
 * #by.nc.nd
 * 2=Attribution Non-Commercial No Derivatives License 3.0
 * #by.nc.sa
 * 3=Attribution Non-Commercial Share Alike License 3.0
 *
 * </pre>
 *
 * @User: tietang
 */
public enum LicenseType {
    Attribution((byte) 4, "by"),
    AttributionNoDerivatives((byte) 5, "by-nd"),
    AttributionShareAlike((byte) 6, "by-sa"),
    AttributionNonCommercial((byte) 1, "by-nc"),
    AttributionNonCommercialNoDerivatives((byte) 2, "by-nc-nd"),
    AttributionNonCommercialShareAlike((byte) 3, "by-nc-sa");

    private static Map<Byte, LicenseType> cache = new HashMap<Byte, LicenseType>();
    private static Map<String, LicenseType> cacheByKey = new HashMap<String, LicenseType>();

    static {
        for (LicenseType type : values()) {
            cache.put(type.value, type);
            cacheByKey.put(type.key, type);
        }
    }

    final byte value;
    final String key;

    private LicenseType(byte value, String key) {
        this.value = value;
        this.key = key;
    }

    public byte getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public static LicenseType valueOf(int value) {
        return cache.get(value);
    }

    public static LicenseType findByKey(String key) {
        if (key == null || "".equals(key)) {
            return null;
        }
        LicenseType[] fs = values();
        for (LicenseType enumType : fs) {
            if (enumType.getKey().equalsIgnoreCase(key)) {
                return enumType;
            }
        }
        throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + key);
    }

    public static LicenseType find(String name) {
        if (name == null || "".equals(name)) {
            return null;
        }
        LicenseType[] fs = values();
        for (LicenseType enumType : fs) {
            if (enumType.name().equalsIgnoreCase(name)) {
                return enumType;
            }

        }
        throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + name);
    }
}
