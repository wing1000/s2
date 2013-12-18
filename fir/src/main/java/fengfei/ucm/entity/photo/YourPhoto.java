package fengfei.ucm.entity.photo;

import fengfei.fir.model.PhotoShow;

public class YourPhoto extends PhotoShow {

    public int updateAt;

    public YourPhoto() {
    }

    public YourPhoto(long idPhoto, String title, int idUser, String userName, int updateAt) {
        super();
        this.idPhoto = idPhoto;
        this.title = title;
        this.idUser = idUser;
        this.niceName = userName;
        this.updateAt = updateAt;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(long idPhoto) {
        this.idPhoto = idPhoto;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(int updateAt) {
        this.updateAt = updateAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return niceName;
    }

    public void setUserName(String userName) {
        this.niceName = userName;
    }

    @Override
    public String toString() {
        return "YourPhoto [idPhoto=" + idPhoto + ", idUser=" + idUser + ", updatedAt=" + updateAt
                + "]";
    }

}
