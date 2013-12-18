package fengfei.ucm.entity.photo;

public class PhotoInfo {

    public long idPhoto;
    public String title;
    public long idUser;
    public String userName;
    public int updateAt;
    //
    public String path;

    public PhotoInfo(long idPhoto, String title, long idUser, int updateAt) {
        super();
        this.idPhoto = idPhoto;
        this.title = title;
        this.idUser = idUser;
        this.updateAt = updateAt;
    }

}
