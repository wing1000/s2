package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.StringTransducer;
import fengfei.forest.database.dbutils.impl.ForestRunner.InsertResultSet;
import fengfei.ucm.dao.transducer.PhotoTransducer;
import fengfei.ucm.dao.transducer.YourPhotoTransducer;
import fengfei.ucm.entity.photo.Photo;
import fengfei.ucm.entity.photo.Rank;
import fengfei.ucm.entity.photo.Refresh;
import fengfei.ucm.entity.photo.YourPhoto;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PhotoDao {

    final static String IncrCommentCount = "update photo%s set comment_count=comment_count+1 where id_photo=?";
    final static String Insert = "INSERT INTO photo%s"
            + "(id_photo, id_user, title, `desc`, category, adult,copyright,license,can_ps,jiff,"
            + "tags,create_at, create_at_gmt, update_at, status,comment_count, original_at,"
            + " make, model, aperture, shutter, iso, lens, focus,  ev"
            + ", white_balance, software, flash, color_space, metering_mode, exposure_mode, exposure_program, gps_latitude, gps_longitude, gps_altitude, gps_origin,ip"
            + ")" + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    final static String Update = "update photo%s"
            + " set original_at=?,make=?,model=?,aperture=?,shutter=?,iso=?,lens=?,focus=?,ev=?,"
            + " white_balance = ?, software = ?, flash = ?, color_space = ?, metering_mode = ?,"
            + " exposure_mode = ?, exposure_program = ?, gps_latitude = ?, gps_longitude = ?, gps_altitude = ?, gps_origin = ? ,ip=?, "
            + " title=?, `desc`=?,category=?,adult=?,copyright=?,license=?,can_ps=?,jiff=?,tags=?,update_at=?,status=? "
            + " where id_photo=? and id_user=?";

    public static List<InsertResultSet<Long>> save(
            ForestGrower grower,
            String suffix,
            String userName,
            List<Photo> models) throws SQLException {

        List<InsertResultSet<Long>> insertResultSets = new ArrayList<>();
        for (Photo exifModel : models) {
            InsertResultSet<Long> u = saveOne(grower, suffix, exifModel, userName);
            insertResultSets.add(u);
        }

        return insertResultSets;
    }

    public static int incrCommentCount(ForestGrower grower, String suffix, long idPhoto)
            throws SQLException {
        int updated = grower.update(String.format(IncrCommentCount, suffix), idPhoto);
        return updated;

    }

    //

    public static InsertResultSet<Long> saveOne(
            ForestGrower grower,
            String suffix,
            Photo m,
            String userName) throws SQLException {
        String sql = "SELECT  id_photo   FROM photo" + suffix + " WHERE id_photo=? for update";
        String id = grower.selectOne(sql, new StringTransducer(), m.idPhoto);
        InsertResultSet<Long> u = null;

        if (id == null || "".equals(id)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createAtGmt = sdf.format(m.createdAtGmt);
            String insert = String.format(Insert, suffix);

            int up = grower.update(
                    insert,
                    m.idPhoto,
                    m.idUser,
                    m.title,
                    m.description,
                    (int) m.category,
                    m.adult,
                    m.copyright, m.license, m.canPS,m.jiff,
                    m.tags,
                    m.createdAt,
                    createAtGmt,
                    m.updatedAt, m.status,
                    0,
                    m.dateTimeOriginal,
                    m.make,
                    m.model,
                    m.aperture,
                    m.shutter,
                    m.iso,
                    m.lens,
                    m.focus,
                    m.ev,
                    m.WhiteBalance,
                    m.Software,
                    m.Flash,
                    m.ColorSpace,
                    m.MeteringMode,
                    m.ExposureMode,
                    m.ExposureProgram,
                    m.GPSLatitude,
                    m.GPSLongitude,
                    m.GPSAltitude,
                    m.GPSOrigin,
                    m.iip);
            Refresh refresh = new Refresh(m.idPhoto, m.title, m.idUser, userName, m.createdAt);
            refresh.category = m.category;
            int updated = PhotoExtDao.addRefresh(grower, suffix, refresh);
            Rank rank = new Rank(m.idPhoto, m.idUser, 0, 0, 0, 0, 0, m.createdAt);
            rank.title = m.title;
            rank.niceName = userName;
            RankDao.addRank(grower, suffix, rank);
            u = new InsertResultSet<Long>(1, m.idPhoto);

        } else {

            String update = String.format(Update, suffix);
            int updated = grower.update(
                    update,
                    m.dateTimeOriginal,
                    m.make,
                    m.model,
                    m.aperture,
                    m.shutter,
                    m.iso,
                    m.lens,
                    m.focus,
                    m.ev,
                    m.WhiteBalance,
                    m.Software,
                    m.Flash,
                    m.ColorSpace,
                    m.MeteringMode,
                    m.ExposureMode,
                    m.ExposureProgram,
                    m.GPSLatitude,
                    m.GPSLongitude,
                    m.GPSAltitude,
                    m.GPSOrigin,
                    m.iip,
                    m.title,
                    m.description,
                    m.category,
                    m.adult,
                    m.copyright, m.license, m.canPS,m.jiff,
                    m.tags,
                    m.updatedAt, m.status,
                    m.idPhoto,
                    m.idUser);
            u = new InsertResultSet<Long>(updated, null);
        }

        return u;
    }

    public static int updateOne(ForestGrower grower, String suffix, Photo m, String userName)
            throws SQLException {
        String update = String.format(Update, suffix);
        int updated = grower.update(
                update,
                m.dateTimeOriginal,
                m.make,
                m.model,
                m.aperture,
                m.shutter,
                m.iso,
                m.lens,
                m.focus,
                m.ev,
                m.WhiteBalance,
                m.Software,
                m.Flash,
                m.ColorSpace,
                m.MeteringMode,
                m.ExposureMode,
                m.ExposureProgram,
                m.GPSLatitude,
                m.GPSLongitude,
                m.GPSAltitude,
                m.GPSOrigin,
                m.iip,
                m.title,
                m.description,
                m.category,
                m.adult,
                m.copyright, m.license, m.canPS,m.jiff,
                m.tags,
                m.updatedAt,
                m.idPhoto,
                m.idUser);
        return updated;
    }

    public static InsertResultSet<Long> addOne(
            ForestGrower grower,
            String suffix,
            Photo m,
            String userName) throws SQLException {
        String id = null;
        if (m.idPhoto > 0) {
            String sql = "SELECT  id_photo  FROM photo" + suffix + " WHERE id_photo=?";
            id = grower.selectOne(sql, new StringTransducer(), m.idPhoto);
        }
        System.out.println("======: " + m.idPhoto);
        InsertResultSet<Long> u = null;

        if (id == null || "".equals(id)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createAtGmt = sdf.format(m.createdAtGmt);
            String insert = String.format(Insert, suffix);
            int up = grower.update(
                    insert,
                    m.idPhoto,
                    m.idUser,
                    m.title,
                    m.description,
                    (int) m.category,
                    m.adult,
                    m.copyright, m.license, m.canPS,m.jiff,
                    m.tags,
                    m.createdAt,
                    createAtGmt,
                    m.updatedAt,
                    0,
                    m.dateTimeOriginal,
                    m.make,
                    m.model,
                    m.aperture,
                    m.shutter,
                    m.iso,
                    m.lens,
                    m.focus,
                    m.ev,
                    m.WhiteBalance,
                    m.Software,
                    m.Flash,
                    m.ColorSpace,
                    m.MeteringMode,
                    m.ExposureMode,
                    m.ExposureProgram,
                    m.GPSLatitude,
                    m.GPSLongitude,
                    m.GPSAltitude,
                    m.GPSOrigin,
                    m.iip);

            u = new InsertResultSet<Long>(1, m.idPhoto);
        } else {

            String update = String.format(Update, suffix);
            int updated = grower.update(
                    update,
                    m.dateTimeOriginal,
                    m.make,
                    m.model,
                    m.aperture,
                    m.shutter,
                    m.iso,
                    m.lens,
                    m.focus,
                    m.ev,
                    m.WhiteBalance,
                    m.Software,
                    m.Flash,
                    m.ColorSpace,
                    m.MeteringMode,
                    m.ExposureMode,
                    m.ExposureProgram,
                    m.GPSLatitude,
                    m.GPSLongitude,
                    m.GPSAltitude,
                    m.GPSOrigin,
                    m.iip,
                    m.title,
                    m.description,
                    m.category,
                    m.adult,
                    m.copyright, m.license, m.canPS,m.jiff,
                    m.tags,
                    m.updatedAt,
                    m.idPhoto,
                    m.idUser);
            u = new InsertResultSet<Long>(updated, m.idPhoto);
        }

        return u;
    }

   public final static String SelectPhotoClause = "SELECT id_photo,id_user,nice_name,title,`desc`,category,"
            + "create_at,create_at_gmt,update_at,status,comment_count,adult,copyright,`license`,can_ps,jiff,tags,"
            + "make,model,lens,aperture,shutter,iso,focus,ev,original_at,white_balance,software,flash,"
            + "color_space,metering_mode,exposure_mode,exposure_program,gps_latitude,gps_longitude,gps_altitude,gps_origin,ip";

    final static String SelectOneByIdAndUserId = SelectPhotoClause + " FROM photo%s WHERE id_photo=? and id_user=?";

    public static Photo selectOne(ForestGrower grower, String suffix, long idPhoto, long idUser)
            throws SQLException {

        Photo exifModel = grower.selectOne(String.format(SelectOneByIdAndUserId, suffix), new PhotoTransducer(), idPhoto, idUser);
        return exifModel;

    }

    final static String SelectOne = SelectPhotoClause + " FROM photo%s WHERE id_photo=? ";

    public static Photo selectOne(ForestGrower grower, String suffix, long idPhoto)
            throws SQLException {

        Photo exifModel = grower.selectOne(String.format(SelectOne, suffix), new PhotoTransducer(), idPhoto);
        return exifModel;

    }

    public static List<YourPhoto> selectUserPhoto(
            ForestGrower grower,
            String suffix,
            Integer idUser) throws SQLException {
        String sql = "SELECT id_photo, id_user, title, update_at FROM photo" + suffix
                + " WHERE id_user=?";
        List<YourPhoto> photos = grower.select(sql, new YourPhotoTransducer(), idUser);
        return photos;

    }

}
