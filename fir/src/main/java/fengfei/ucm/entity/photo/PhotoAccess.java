package fengfei.ucm.entity.photo;

import fengfei.fir.model.PhotoShow;

import java.util.HashMap;
import java.util.Map;

public class PhotoAccess extends PhotoShow{

    /**
     * 访问类型1=view,2=vote,3=favorite
     * 
     * @author
     * 
     */
    public static enum AccessType {
        View(1), Vote(2), Favorite(3), Comment(4), Score(5);

        private final int value;
        private static Map<Integer, AccessType> cache = new HashMap<Integer, AccessType>();
        static {
            for (AccessType type : values()) {
                cache.put(type.value, type);
            }
        }

        private AccessType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static AccessType valueOf(int value) {
            return cache.get(value);
        }

        public static AccessType find(String name) {
            if (name == null || "".equals(name)) {
                return null;
            }
            AccessType[] fs = values();
            for (AccessType enumType : fs) {
                if (enumType.name().equalsIgnoreCase(name)) {
                    return enumType;
                }

            }
            throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + name);
        }
    }

    public long id;
    public int updateAt;
    public int cancel = 0;
    public int iip;
    public String ip;
    public Integer accessIdUser;
    public AccessType type;

    public PhotoAccess() {
    }

    public PhotoAccess(
        long id,
        long idPhoto,
        Integer idUser,
        int updateAt,
        int cancel,
        AccessType type) {
        super();
        this.id = id;
        this.idPhoto = idPhoto;
        this.idUser = idUser;
        this.updateAt = updateAt;
        this.cancel = cancel;

        this.type = type;
    }

    public PhotoAccess(
        long idPhoto,
        String title,
        Integer idUser,
        String username,
        int updateAt,
        int cancel,

        AccessType type) {
        super();
        this.idPhoto = idPhoto;
        this.idUser = idUser;
        this.updateAt = updateAt;
        this.cancel = cancel;
        this.title = title;
        this.niceName = username;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return niceName;
    }

    public void setUsername(String username) {
        this.niceName = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccessType getType() {
        return type;
    }

    public void setType(AccessType type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(long idPhoto) {
        this.idPhoto = idPhoto;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public int getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(int updateAt) {
        this.updateAt = updateAt;
    }

    public int getCancel() {
        return cancel;
    }

    public void setCancel(int cancel) {
        this.cancel = cancel;
    }

    @Override
    public String toString() {
        return "Vote [idPhoto=" + idPhoto + ", idUser=" + idUser + ", updatedAt=" + updateAt
                + ", cancel=" + cancel + "]";
    }

}
