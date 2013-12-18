package fengfei.ucm.entity.photo;

import fengfei.fir.model.PhotoShow;

import java.sql.Date;

public class Rank extends PhotoShow {

    public Date day;
    public int view;
    public int vote;
    public int favorite;
    public int comment;
    public double score;
    public long updateAt;
    public double maxScore;
    public long maxAt;
    public int affection;

    public Rank() {
    }

    public Rank(
        long idPhoto,
        int idUser,
        int view,
        int vote,
        int favorite,
        int comment,
        double score,
        long updateAt) {
        super();
        this.idPhoto = idPhoto;
        this.idUser = idUser;
        this.view = view;
        this.vote = vote;
        this.favorite = favorite;
        this.comment = comment;
        this.score = score;
        this.updateAt = updateAt;
    }

    public Rank(
        long idPhoto,
        Date day,
        int idUser,
        int view,
        int vote,
        int favorite,
        int comment,
        double score,
        long updateAt) {
        super();
        this.idPhoto = idPhoto;
        this.day = day;
        this.idUser = idUser;
        this.view = view;
        this.vote = vote;
        this.favorite = favorite;
        this.comment = comment;
        this.score = score;
        this.updateAt = updateAt;
    }

    public Rank(
        int idUser,
        int view,
        int vote,
        int favorite,
        int comment,
        long updateAt,
        int affection) {
        super();
        this.idUser = idUser;
        this.view = view;
        this.vote = vote;
        this.favorite = favorite;
        this.comment = comment;
        this.updateAt = updateAt;
        this.affection = affection;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public long getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(long idPhoto) {
        this.idPhoto = idPhoto;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "Rank [idPhoto=" + idPhoto + ", day=" + day + ", idUser=" + idUser + ", view="
                + view + ", vote=" + vote + ", favorite=" + favorite + ", comment=" + comment
                + ", score=" + score + ", updatedAt=" + updateAt + ", maxScore=" + maxScore
                + ", maxAt=" + maxAt + ", title=" + title + ", userName=" + niceName + ", path="
                + path + ", sscore=" + sscore + "]";
    }

}
