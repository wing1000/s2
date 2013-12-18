package fengfei.ucm.entity.photo;

import fengfei.fir.model.PhotoShow;

public class Favorite extends PhotoShow{

    public long id;
    public int updateAt;
    public int cancel = 0;
    public String ip;
    public int iip;
    public Integer accessIdUser;

    public Favorite() {
    }

    public Favorite(long id, long idPhoto, Integer idUser, int updateAt, int cancel) {
        super();
        this.id = id;
        this.idPhoto = idPhoto;
        this.idUser = idUser;
        this.updateAt = updateAt;
        this.cancel = cancel;

    }

    public Favorite(long idPhoto, Integer idUser, int updateAt, int cancel) {
        super();
        this.idPhoto = idPhoto;
        this.idUser = idUser;
        this.updateAt = updateAt;
        this.cancel = cancel;

    }

    public Favorite(
        long idPhoto,
        String title,
        Integer idUser,
        String niceName,
        int updateAt,
        int cancel) {
        super();
        this.idPhoto = idPhoto;
        this.title = title;
        this.idUser = idUser;
        this.niceName = niceName;
        this.updateAt = updateAt;
        this.cancel = cancel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return "Favorite [id=" + id + ", idPhoto=" + idPhoto + ", category=" + category
                + ", title=" + title + ", idUser=" + idUser + ", username=" + niceName
                + ", updatedAt=" + updateAt + ", cancel=" + cancel + ", ip=" + ip
                + ", accessIdUser=" + accessIdUser + "]";
    }

}
