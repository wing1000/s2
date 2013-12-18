package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.LongTransducer;
import fengfei.ucm.dao.transducer.FavoriteTransducer;
import fengfei.ucm.entity.photo.Canceled;
import fengfei.ucm.entity.photo.Favorite;

import java.sql.SQLException;
import java.util.List;

public class PhotoFavoriteDao {

    final static String InsertFavorite = "INSERT INTO photo_favorite%s(id_photo,category, title,id_user,nice_name, at, cancel, ip,access_id_user)"
            + " VALUES (?,?,?,?,?,?,?,?,?)";

    final static String UpdateFavoriteByIP = "UPDATE photo_favorite%s"
            + " SET at = ?, cancel = ?, ip = ?    WHERE id_photo=? and ip=? and access_id_user =0 ";
    final static String UpdateFavoriteByUser = "UPDATE photo_favorite%s"
            + " SET at = ?, cancel = ?, ip = ?   WHERE id_photo=? and access_id_user=? ";

    final static String CancelFavoriteByIP = "UPDATE photo_favorite%s  SET cancel = ? WHERE id_photo=? and ip=? and access_id_user =0 ";
    final static String CancelFavoriteByUser = "UPDATE photo_favorite%s  SET cancel = ? WHERE id_photo=?   and access_id_user=? ";
    final static String ExsitsSelectFavoriteByIP = "SELECT id FROM photo_favorite%s where id_photo=? and ip=? and access_id_user =0 and cancel=?";
    final static String ExsitsSelectFavoriteByUser = "SELECT id FROM photo_favorite%s where id_photo=?  and access_id_user=? and cancel=?";
    final static String SelectFavoriteByIPForLock = "SELECT id,id_photo,category,title, id_user,nice_name, at, cancel, ip,access_id_user  "
            + " FROM photo_favorite%s where id_photo=? and ip=? and access_id_user =0 for update ";
    final static String SelectFavoriteByUserForLock = "SELECT id,id_photo,category,title, id_user,nice_name, at, cancel, ip,access_id_user  "
            + " FROM photo_favorite%s where id_photo=?  and access_id_user=? for update ";

    final static String SelectFavoriteByUser = "SELECT id,id_photo,category,title, id_user,nice_name, at, cancel, ip,access_id_user FROM photo_favorite%s where id_user=? limit ?,?";
    final static String SelectFavoriteByIP = "SELECT id,id_photo,category,title, id_user,nice_name, at, cancel, ip,access_id_user FROM photo_favorite%s where ip=? and access_id_user=0 limit ?,?";

    public static int cancelPhotoFavorite(
        ForestGrower grower,
        String suffix,
        long idPhoto,
        Integer accessIdUser,
        int iip) throws SQLException {

        int updated = 0;
        if (accessIdUser == null || accessIdUser <= 0) {
            Favorite favorite = getFavoriteForLock(grower, suffix, accessIdUser, idPhoto, iip);
            if (favorite.accessIdUser == null) {
                String update = String.format(CancelFavoriteByIP, suffix);
                updated = grower.update(update, Canceled.Yes.getValue(), idPhoto, iip);
            }

        } else {
            String update = String.format(CancelFavoriteByUser, suffix);
            updated = grower.update(update, Canceled.Yes.getValue(), idPhoto, accessIdUser);
        }
        return updated;

    }

    public static int[] addPhotoFavorite(ForestGrower grower, String suffix, Favorite m)
        throws SQLException {
        // Long id = grower.selectOne(
        // String.format(SelectFavorite, suffix),
        // new LongTransducer(),
        // m.idPhoto,
        // m.accessIdUser,
        // m.ip);
        Favorite favorite = getFavoriteForLock(grower, suffix, m.accessIdUser, m.idPhoto, m.iip);
        System.out.println(favorite);
        if (favorite == null) {
            String insert = String.format(InsertFavorite, suffix);
            int updated = grower.update(
                insert,
                m.idPhoto,
                (int) m.category,
                m.title,
                m.idUser,
                m.niceName,
                m.updateAt,
                m.cancel,
                m.iip,
                m.accessIdUser==null?0:m.accessIdUser);
            return new int[] { 1, updated };

        } else {
            int updated = 0;
            if (m.accessIdUser == null || m.accessIdUser <= 0) {
                String update = String.format(UpdateFavoriteByIP, suffix);
                updated = grower.update(
                    update,
                    m.updateAt,
                    Canceled.No.getValue(),
                    m.iip,
                    m.idPhoto,
                    m.iip);
            } else {
                String update = String.format(UpdateFavoriteByUser, suffix);
                updated = grower.update(
                    update,
                    m.updateAt,
                    Canceled.No.getValue(),
                    m.iip,
                    m.idPhoto,
                    m.accessIdUser);
            }

            if (favorite.cancel == Canceled.Yes.getValue()) {
                return new int[] { 1, updated };
            } else {
                return new int[] { 0, updated };
            }

        }
    }

    public static Favorite getFavoriteForLock(
        ForestGrower grower,
        String suffix,
        Integer accessIdUser,
        long idPhoto,
        int iip) throws SQLException {
        Favorite favorite = null;
        if (accessIdUser == null || accessIdUser <= 0) {
            favorite = grower.selectOne(
                String.format(SelectFavoriteByIPForLock, suffix),
                new FavoriteTransducer(),
                idPhoto,
                iip);
        } else {
            favorite = grower.selectOne(
                String.format(SelectFavoriteByUserForLock, suffix),
                new FavoriteTransducer(),
                idPhoto,

                accessIdUser);
        }

        return favorite;
    }

    public static boolean isFavorite(
        ForestGrower grower,
        String suffix,
        Integer accessIdUser,
        long idPhoto,
        int iip) throws SQLException {
        Long id = null;
        if (accessIdUser == null || accessIdUser <= 0) {
            id = grower.selectOne(
                String.format(ExsitsSelectFavoriteByIP, suffix),
                new LongTransducer(),
                idPhoto,
                iip,
                Canceled.No.getValue());
        } else {
            id = grower.selectOne(
                String.format(ExsitsSelectFavoriteByUser, suffix),
                new LongTransducer(),
                idPhoto,

                accessIdUser,
                Canceled.No.getValue());
        }

        return id != null;
    }

    public static List<Favorite> selectFavoritesByUser(
        ForestGrower grower,
        String suffix,
        Integer idUser,
        int offset,
        int limit) throws SQLException {
        String sql = String.format(SelectFavoriteByUser, suffix);
        List<Favorite> favorites = grower.select(
            sql,
            new FavoriteTransducer(),
            idUser,
            offset,
            limit);
        return favorites;

    }

    public static List<Favorite> selectFavoritesByIP(
        ForestGrower grower,
        String suffix,
        int iip,
        int offset,
        int limit) throws SQLException {
        String sql = String.format(SelectFavoriteByIP, suffix);
        List<Favorite> favorites = grower.select(sql, new FavoriteTransducer(), iip, offset, limit);
        return favorites;

    }
}
