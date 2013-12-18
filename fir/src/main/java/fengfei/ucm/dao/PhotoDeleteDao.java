package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;

import java.sql.SQLException;

public class PhotoDeleteDao {

    final static String DeletePhoto = "delete from photo%s where id_photo=?";
    final static String DeletePhotoRank = "delete from photo_rank%s where id_photo=?";
    //
    final static String DeletePhotoRefresh = "delete from photo_refresh%s where id_photo=?";
    final static String DeletePhotoChoice = "delete from photo_choice%s where id_photo=?";
    final static String DeletePhotoRanks = "delete from photo_ranks%s where id_photo=?";
    final static String DeletePhotoComments = "delete from photo_comments%s where id_photo=?";
    final static String DeletePhotoAccess = "delete from photo_access%s where id_photo=?";
    final static String DeletePhotoFavorite = "delete from photo_favorite%s where id_photo=?";
    final static String DeletePhotoSets = "delete from photo_sets%s where id_photo=?";

    public static boolean deleteOne(
        ForestGrower grower,
        String suffix,
        long idPhoto,
        Integer idUser) throws SQLException {
        // 绝对存在
        int u1 = grower.update(String.format(DeletePhoto, suffix), idPhoto);
        int u2 = grower.update(String.format(DeletePhotoRank, suffix), idPhoto);
        // 可能不存在
        int up1 = grower.update(String.format(DeletePhotoRefresh, suffix), idPhoto);
        int up2 = grower.update(String.format(DeletePhotoChoice, suffix), idPhoto);
        int up5 = grower.update(String.format(DeletePhotoRanks, suffix), idPhoto);
        int up6 = grower.update(String.format(DeletePhotoComments, suffix), idPhoto);
        int up7 = grower.update(String.format(DeletePhotoAccess, suffix), idPhoto);
        int up8 = grower.update(String.format(DeletePhotoFavorite, suffix), idPhoto);
        int up9 = grower.update(String.format(DeletePhotoSets, suffix), idPhoto);
        int up10 = PhotoSetDao.deletePhotoSets(grower, suffix, idUser, idPhoto);
        return (u1 > 0) && (u2 > 0);
    }

}
