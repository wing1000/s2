package fengfei.ucm.entity.photo;

public class PhotoSet {

    public long idSet;
    public String name;
    public Integer idUser;
    public int createAt;
    public long updateAt;

    @Override
    public String toString() {
        return "PhotoSet [idSet=" + idSet + ", name=" + name + ", idUser=" + idUser
                + ", createdAt=" + createAt + ", updatedAt=" + updateAt + "]";
    }
}
