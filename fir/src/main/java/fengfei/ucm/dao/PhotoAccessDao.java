package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.LongTransducer;
import fengfei.ucm.dao.transducer.PhotoAccessTransducer;
import fengfei.ucm.entity.photo.Canceled;
import fengfei.ucm.entity.photo.PhotoAccess;
import fengfei.ucm.entity.photo.PhotoAccess.AccessType;

import java.sql.SQLException;
import java.util.List;

public class PhotoAccessDao {

    final static String InsertPhotoAccess = "INSERT INTO photo_access%s(id_photo,category,title, id_user,nice_name, at, cancel, ip,access_id_user, type)"
            + "VALUES (?,?,?,?,?,?,?,?,?,?)";
    final static String UpdatePhotoAccess = "UPDATE photo_access%s"
            + "SET at = ?, cancel = ?, ip = ?,access_id_user=? "
            + "WHERE id_photo=? and id_user=? and type=?";
    final static String CancelPhotoAccess = "UPDATE photo_access%s"
            + "SET   cancel = ? WHERE id_photo=? and id_user=? and type=?";
    // final static String SelectPhotoAccess =
    // "SELECT id FROM photo_access%s where id_photo=? and ( access_id_user=? or ip=?)";
    final static String ExistsPhotoAccessByIP = "SELECT id FROM photo_access%s where id_photo=? and type=? and  ip=? and access_id_user=0  ";
    final static String ExistsPhotoAccessByID = "SELECT id FROM photo_access%s where id_photo=? and type=? and  access_id_user=?   ";
    final static String ExistsPhotoAccess = "SELECT id FROM photo_access%s where id_photo=? and type=? and  ( access_id_user=? or ip=?) ";
    final static String ExistsPhotoAccessByIPForLock = "SELECT id FROM photo_access%s where id_photo=? and type=? and ip=? and access_id_user=0 for update";
    final static String ExistsPhotoAccessByIDForLock = "SELECT id FROM photo_access%s where id_photo=? and type=? and access_id_user=?  for update ";
    final static String SelectPhotoAccessByUserId = "SELECT id,id_photo,category,title, id_user, nice_name,at, cancel, ip, type FROM photo_access%s where access_id_user=? limit ?,?";
    final static String SelectPhotoAccessByIP = "SELECT id,id_photo,category,title, id_user, nice_name,at, cancel, ip, type FROM photo_access%s where access_id_user=0 and ip=? limit ?,?";
    final static String SelectPhotoAccess = "SELECT id,id_photo,category,title, id_user, nice_name,at, cancel, ip, type FROM photo_access%s limit ?,?";
    final static String SelectPhotoViewsByUserId = "SELECT id,id_photo,category,title, id_user, nice_name,at, cancel, ip, type FROM photo_access%s where access_id_user=? and type=? limit ?,?";
    final static String SelectPhotoViewsByIP = "SELECT id,id_photo,category,title, id_user, nice_name,at, cancel, ip, type FROM photo_access%s where access_id_user=0 and ip=? and type=? limit ?,?";

    public static int updatePhotoAccess(ForestGrower grower, String suffix, PhotoAccess m)
        throws SQLException {

        String update = String.format(UpdatePhotoAccess, suffix);
        int updated = grower.update(
            update,
            m.updateAt,
            m.cancel,
            m.iip,
            m.accessIdUser,
            m.idPhoto,
            m.idUser,
            m.type.getValue());
        return updated;

    }

    public static int cancelPhotoAccess(
        ForestGrower grower,
        String suffix,
        long idPhoto,
        int idUser,
        AccessType type) throws SQLException {

        String update = String.format(CancelPhotoAccess, suffix);
        int updated = grower.update(
            update,
            Canceled.Yes.getValue(),
            idPhoto,
            idUser,
            type.getValue());
        return updated;

    }

    public static int addPhotoAccess(ForestGrower grower, String suffix, PhotoAccess m)
        throws SQLException {
        // Long id = grower.selectOne(String.format(ExistsPhotoAccess, suffix),
        // new LongTransducer(), m.idPhoto, m.type.getValue(), m.idUser,
        // m.ip);
        boolean isExists = isAccessedForLock(
            grower,
            suffix,
            m.accessIdUser,
            m.idPhoto,
            m.iip,
            m.type);
        if (!isExists) {

            // if (id == null) {
            String insert = String.format(InsertPhotoAccess, suffix);
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
                m.accessIdUser == null ? 0 : m.accessIdUser,
                m.type.getValue());
            return updated;

        } else {
            return 0;

        }

    }

    public static boolean isAccessedForLock(
        ForestGrower grower,
        String suffix,
        Integer accessIdUser,
        long idPhoto,
        int iip,
        AccessType type) throws SQLException {

        Long id = null;
        if (accessIdUser == null || accessIdUser <= 0) {
            id = grower.selectOne(
                String.format(ExistsPhotoAccessByIPForLock, suffix),
                new LongTransducer(),
                idPhoto,
                type.getValue(),
                iip);
        } else {
            id = grower.selectOne(
                String.format(ExistsPhotoAccessByIDForLock, suffix),
                new LongTransducer(),
                idPhoto,
                type.getValue(),
                accessIdUser);
        }

        return id != null;
    }

    public static boolean isAccessed(
        ForestGrower grower,
        String suffix,
        Integer accessIdUser,
        long idPhoto,
        int iip,
        AccessType type) throws SQLException {
        Long id = null;
        if (accessIdUser == null || accessIdUser <= 0) {
            id = grower.selectOne(
                String.format(ExistsPhotoAccessByIP, suffix),
                new LongTransducer(),
                idPhoto,
                type.getValue(),
                iip);
        } else {
            id = grower.selectOne(
                String.format(ExistsPhotoAccessByID, suffix),
                new LongTransducer(),
                idPhoto,
                type.getValue(),
                accessIdUser);
        }

        return id != null;
    }

    public static List<PhotoAccess> selectPhotoAccessesByUser(
        ForestGrower grower,
        String suffix,
        int idUser,
        int offset,
        int limit) throws SQLException {
        String sql = String.format(SelectPhotoAccessByUserId, suffix);
        List<PhotoAccess> favorites = grower.select(
            sql,
            new PhotoAccessTransducer(),
            idUser,
            offset,
            limit);
        return favorites;

    }

    public static List<PhotoAccess> selectPhotoAccessesByIP(
        ForestGrower grower,
        String suffix,
        int iip,
        int offset,
        int limit) throws SQLException {
        String sql = String.format(SelectPhotoAccessByIP, suffix);
        List<PhotoAccess> favorites = grower.select(
            sql,
            new PhotoAccessTransducer(),
            iip,
            offset,
            limit);
        return favorites;

    }

    public static List<PhotoAccess> selectPhotoAccesses(
        ForestGrower grower,
        String suffix,
        int offset,
        int limit) throws SQLException {
        String sql = String.format(SelectPhotoAccess, suffix);
        List<PhotoAccess> favorites = grower.select(
            sql,
            new PhotoAccessTransducer(),
            offset,
            limit);
        return favorites;

    }

    public static List<PhotoAccess> selectPhotoViewsByUser(
        ForestGrower grower,
        String suffix,
        int idUser,
        int offset,
        int limit) throws SQLException {
        String sql = String.format(SelectPhotoViewsByUserId, suffix);
        List<PhotoAccess> favorites = grower.select(
            sql,
            new PhotoAccessTransducer(),
            idUser,
            PhotoAccess.AccessType.View.getValue(),
            offset,
            limit);
        return favorites;

    }

    public static List<PhotoAccess> selectPhotoViewsByIP(
        ForestGrower grower,
        String suffix,
        int iip,
        int offset,
        int limit) throws SQLException {
        String sql = String.format(SelectPhotoViewsByIP, suffix);
        List<PhotoAccess> favorites = grower.select(
            sql,
            new PhotoAccessTransducer(),
            iip,
            PhotoAccess.AccessType.View.getValue(),
            offset,
            limit);
        return favorites;

    }
}
