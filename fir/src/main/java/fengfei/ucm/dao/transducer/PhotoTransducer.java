package fengfei.ucm.dao.transducer;

import fengfei.fir.utils.AppUtils;
import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.Photo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PhotoTransducer implements Transducer<Photo> {

    @Override
    public Photo transform(ResultSet rs) throws SQLException {

        long idPhoto = rs.getLong("id_photo");
        int idUser = rs.getInt("id_user");
        String title = rs.getString("title");
        String description = rs.getString("desc");
        byte category = rs.getByte("category");
        int adult = rs.getInt("adult");
        int copyright = rs.getInt("copyright");
        int createAt = rs.getInt("create_at");
        Timestamp createAtGmt = rs.getTimestamp("create_at_gmt");
        long updateAt = rs.getLong("update_at");
        byte status = rs.getByte("status");
        int commentCount = rs.getInt("comment_count");
        byte license = rs.getByte("license");
        byte canPS = rs.getByte("can_ps");
        byte jiff = rs.getByte("jiff");
        //
        String make = rs.getString("make");
        String model = rs.getString("model");
        String aperture = rs.getString("aperture");
        String shutter = rs.getString("shutter");
        String iso = rs.getString("iso");
        String lens = rs.getString("lens");
        String focus = rs.getString("focus");
        String ev = rs.getString("ev");
        String tags = rs.getString("tags");
        String dateTimeOriginal = rs.getString("original_at");
        //

        Photo photo = new Photo(
                idPhoto,
                idUser,
                title,
                description,
                category,
                adult,
                copyright,
                tags,
                createAt,
                createAtGmt,
                updateAt,
                commentCount,
                make,
                model,
                aperture,
                shutter,
                iso,
                lens,
                focus,
                ev,
                dateTimeOriginal);
        photo.license = license;
        photo.canPS = canPS;
        photo.status = status;
        photo.jiff = jiff;
        photo.WhiteBalance = rs.getShort("white_balance");
        photo.Software = rs.getString("software");
        photo.Flash = rs.getShort("flash");
        photo.ColorSpace = rs.getShort("color_space");
        photo.MeteringMode = rs.getShort("metering_mode");
        photo.ExposureProgram = rs.getShort("exposure_program");
        photo.ExposureMode = rs.getShort("exposure_mode");
        photo.GPSLatitude = rs.getDouble("gps_latitude");
        photo.GPSLongitude = rs.getDouble("gps_longitude");
        photo.GPSAltitude = rs.getDouble("gps_altitude");
        photo.GPSOrigin = rs.getByte("gps_origin");
        photo.iip = rs.getInt("ip");
        photo.ip = AppUtils.int2ip(photo.iip);
        return photo;
    }
}
