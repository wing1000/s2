package fengfei.ucm.entity.photo;

public class PhotoSearch {

    public long idSearch;
    public long idPhoto;
    public long idUser;

    public PhotoSearch(long idSearch, long idPhoto, long idUser) {
        this.idSearch = idSearch;
        this.idPhoto = idPhoto;
        this.idUser = idUser;
    }

    public PhotoSearch() {
    }
}
