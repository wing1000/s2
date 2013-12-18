package fengfei.ucm.entity.photo;

import java.util.HashMap;
import java.util.Map;

public class Tag {

    public static enum TagType {
        Photo(1), Story(2), StoryPhoto(3);

        private final int value;
        private static Map<Integer, TagType> cache = new HashMap<Integer, TagType>();
        static {
            for (TagType type : values()) {
                cache.put(type.value, type);
            }
        }

        private TagType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static TagType valueOf(int value) {
            return cache.get(value);
        }

        public static TagType find(String name) {
            if (name == null || "".equals(name)) {
                return null;
            }
            TagType[] fs = values();
            for (TagType enumType : fs) {
                if (enumType.name().equalsIgnoreCase(name)) {
                    return enumType;
                }

            }
            throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + name);
        }
    }

    public long idTag;
    public long id;
    public TagType type;
    public String name;

    public Tag(long id, TagType type, String name) {
        super();

        this.id = id;
        this.type = type;
        this.name = name;
    }

    public Tag() {
    }

    public long getIdTag() {
        return idTag;
    }

    public void setIdTag(long idTag) {
        this.idTag = idTag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TagType getType() {
        return type;
    }

    public void setType(TagType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
