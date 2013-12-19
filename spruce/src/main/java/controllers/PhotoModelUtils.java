package controllers;

import fengfei.fir.utils.AppUtils;
import fengfei.fir.utils.MapUtils;
import fengfei.fir.utils.WebUtils;
import fengfei.sprucy.AppConstants;
import fengfei.ucm.entity.photo.Photo;

import java.sql.Timestamp;
import java.util.Map;

/**
 * @Date: 13-12-18
 * @Time: 下午4:00
 */
public class PhotoModelUtils {
    public final static String KeyIdUser = "idUser";
    public final static String KeyIdPhoto = "id_photo";
    public final static String KeyId = "id";
    public final static String KeyResult = "result";

    public static Photo toPhotoModel(Map<String, String> exifs, Map<String, String> contents) {
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

    static String reOrgTags(String tag) {
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
