package fengfei.fir.service.impl;

import fengfei.fir.service.JpegExifWriter;
import fengfei.fir.utils.AppUtils;
import fengfei.fir.utils.MapUtils;
import fengfei.fir.utils.WebUtils;
import fengfei.forest.database.dbutils.impl.ForestRunner.InsertResultSet;
import fengfei.sprucy.AppConstants;
import fengfei.ucm.entity.photo.Photo;
import fengfei.ucm.repository.PhotoRepository;
import fengfei.ucm.repository.impl.SqlPhotoRepository;
import org.apache.commons.io.FilenameUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class SqlJpegExifWriter implements JpegExifWriter {

    PhotoRepository repository = new SqlPhotoRepository();

    @Override
    public Map<String, Object> writeExif(
            String imageFile,
            Map<String, String> values,
            Map<String, String> contents) throws Exception {
        String title = FilenameUtils.getBaseName(imageFile);
        String userName = contents.get("username");
        Photo model = toPhotoModel(values, contents);

        InsertResultSet<Long> rs = repository.saveOne(model, userName);
        Map<String, Object> rv = new HashMap<>();
        rv.put(KeyId, rs.autoPk);
        rv.put(KeyResult, rs.rows);
        return rv;
    }

    @Override
    public Map<String, Object> updateExif(Map<String, String> contents) throws Exception {
        String userName = contents.get("username");
        Photo model = toPhotoModel(contents, contents);

        int updated = repository.updateOne(model, userName);
        Map<String, Object> rv = new HashMap<>();
        rv.put(KeyId, model.idPhoto);
        rv.put(KeyResult, updated);
        return rv;
    }

    @Override
    public Map<String, Object> writePhoto(
            String imageFile,
            Map<String, String> values,
            Map<String, String> contents) throws Exception {
        String title = FilenameUtils.getBaseName(imageFile);
        String userName = contents.get("username");
        Photo model = toPhotoModel(values, contents);

        InsertResultSet<Long> rs = repository.saveOne(model, userName);
        Map<String, Object> rv = new HashMap<>();
        rv.put(KeyId, rs.autoPk);
        rv.put(KeyResult, rs.rows);
        return rv;
    }

    public Photo toPhotoModel(Map<String, String> exifs, Map<String, String> contents) {
        // System.out.println(exifs);
        // System.out.println(contents);
        long idPhoto = MapUtils.getIntValue(contents, KeyIdPhoto);
        int idUser = MapUtils.getIntValue(contents, KeyIdUser);
        String title = MapUtils.getString(contents, "title", WebUtils.i18n("Untitled"));
        String description = MapUtils.getString(contents, "desc");
        byte category = MapUtils.getByteValue(contents, "category");
        String tags = MapUtils.getString(contents, "tags");
        tags = reOrgTags(tags);
        int adult = MapUtils.getIntValue(contents, "adult");
        int copyright = MapUtils.getIntValue(contents, "copyright");
        //
        byte license = MapUtils.getByteValue(contents, "license");
        byte canPS = MapUtils.getByteValue(contents, "can_ps");

        String make = MapUtils.getString(exifs, "make");
        String model = MapUtils.getString(exifs, "camera");
        String aperture = MapUtils.getString(exifs, "aperture");
        String shutter = MapUtils.getString(exifs, "shutter");
        String iso = MapUtils.getString(exifs, "iso");
        String lens = MapUtils.getString(exifs, "lens");
        String focus = MapUtils.getString(exifs, "focus");
        String ev = MapUtils.getString(exifs, "ev");
        String dateTimeOriginal = MapUtils.getString(exifs, "taken_at");
        //

        long current = System.currentTimeMillis();
        int createAt = (int) (current / 1000);
        Timestamp createAtGmt = new Timestamp(current);

        long updateAt = current;
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
                -1,
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
        photo.niceName = MapUtils.getString(contents, "username");
        photo.idSet = MapUtils.getLongValue(contents, "dir");
        photo.WhiteBalance = MapUtils.getShort(exifs, "WhiteBalance", null);
        photo.Software = MapUtils.getString(exifs, "Software");
        photo.Flash = MapUtils.getShort(exifs, "Flash", null);
        photo.ColorSpace = MapUtils.getShort(exifs, "ColorSpace", null);
        photo.MeteringMode = MapUtils.getShort(exifs, "MeteringMode", null);
        photo.ExposureProgram = MapUtils.getShort(exifs, "ExposureProgram", null);
        photo.ExposureMode = MapUtils.getShort(exifs, "ExposureMode", null);
        photo.GPSLatitude = MapUtils.getDoubleValue(exifs, "GPSLatitude");
        photo.GPSLongitude = MapUtils.getDoubleValue(exifs, "GPSLongitude");
        photo.GPSAltitude = MapUtils.getDoubleValue(exifs, "GPSAltitude");
        photo.GPSOrigin = MapUtils.getByte(exifs, "GPSOrigin", null);
        photo.ip = MapUtils.getString(exifs, "ip");
        photo.iip = AppUtils.ip2int(photo.ip);
        return photo;
    }

    String reOrgTags(String tag) {
        if (tag == null || "".equals(tag)) {
            return "";
        }
        String[] tags = tag.split(AppConstants.TextSplitRegex);
        StringBuilder sb = new StringBuilder();
        for (String str : tags) {
            if (!"".equals(str)) {
                if (sb.length() <= 0) {
                    sb.append(str);
                } else {
                    sb.append(AppConstants.TextTagReSeparator);
                    sb.append(str);
                }
            }

        }
        return sb.toString();

    }
}
