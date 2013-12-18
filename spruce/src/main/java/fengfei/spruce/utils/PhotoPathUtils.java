package fengfei.spruce.utils;

import fengfei.fir.utils.Path;

import java.io.File;

public class PhotoPathUtils {

    public static String getUserPhotoDownloadPath(Integer idUser) {

        return getUserPhotoDownloadPath(idUser == null ? null : new Long(idUser));
    }

    public static String getUserPhotoDownloadPath(Long idUser) {
        if (idUser == null || idUser == 0) {
            return "/public/photo/head/m.jpg";
        }

        StringBuilder sb = Path.getPath(Path.DOWNLOAD_PATH + "/users", idUser);
        sb.append("/");
        sb.append(1);
        sb.append(".jpg");
        String path = sb.toString();
        String jpgPath1 = Path.getHeadPhotoUploadPath(idUser, 1);
        File file = new File(jpgPath1);

        if (!file.exists()) {
            return "/public/photo/head/m.jpg";
        }
        return path;
    }

    public static String getUserPhotoDownloadPath(Integer idUser, int preview) {

        return getUserPhotoDownloadPath(idUser == null ? null : new Long(idUser), preview);
    }

    public static String getUserPhotoDownloadPath(Long idUser, int preview) {
        System.out.println("getUserPhotoDownloadPath: " + idUser);
        if (idUser == null || idUser == 0) {
            return "/public/photo/head/m.jpg";
        }
        StringBuilder sb = Path.getPath(Path.DOWNLOAD_PATH + "/users", idUser);
        sb.append("/");
        sb.append(preview);
        sb.append(".jpg");
        String path = sb.toString();
        String jpgPath1 = Path.getHeadPhotoUploadPath(idUser, preview);
        File file = new File(jpgPath1);
        System.out.println(file.getAbsolutePath());
        if (!file.exists()) {
            return "/public/photo/head/m.jpg";
        }
        return path;
    }

}
