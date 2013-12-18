package fengfei.fir.model;

import fengfei.ucm.entity.photo.Rank;

public class PhotoRank extends PhotoShow {

    public long idPhoto;
    public byte category;
    public String title;
    public int idUser;
    public String niceName;
    public double score;

    public static PhotoRank createPhotoRank(Rank rank) {
        PhotoRank photoRank = new PhotoRank();
        photoRank.idPhoto = rank.idPhoto;
        photoRank.category = rank.category;
        photoRank.title = rank.title;
        photoRank.idUser = rank.idUser;
        photoRank.niceName = rank.niceName;
        photoRank.score = rank.score;
        return photoRank;
    }

    @Override
    public String toString() {
        return "PhotoRank [idPhoto=" + idPhoto + ", category=" + category + ", title=" + title
                + ", idUser=" + idUser + ", niceName=" + niceName + ", score=" + score + "]";
    }

}
