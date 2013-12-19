package controllers.api;

import cn.bran.play.JapidResult;
import controllers.Admin;
import controllers.SecureForJson;
import fengfei.fir.model.Done;
import fengfei.fir.service.JpegExifWriter;
import fengfei.fir.service.JpegProcess;
import fengfei.fir.service.impl.DefaultJpegProcess;
import fengfei.fir.service.impl.SqlJpegExifWriter;
import fengfei.fir.utils.Path;
import fengfei.ucm.entity.profile.Camera;
import fengfei.ucm.repository.ProfileRepository;
import fengfei.ucm.repository.UserRepository;
import fengfei.ucm.repository.impl.SqlProfileRepository;
import fengfei.ucm.repository.impl.SqlUserRepository;
import japidviews.Application.profile.Equipment;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import play.Logger;
import play.modules.router.Delete;
import play.modules.router.Post;
import play.mvc.With;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Date: 13-12-5
 * @Time: 下午1:46
 */
@With(SecureForJson.class)
public class ProfileAPI extends Admin {
    static UserRepository userService = new SqlUserRepository();
    static ProfileRepository profileRepository = new SqlProfileRepository();
    static JpegExifWriter jpegExifWrite = new SqlJpegExifWriter();
    static JpegProcess jpegProcess = new DefaultJpegProcess();


    @Post("/settings/profile/crop/done")
    public static void userPhotoCrop() {
        Map<String, String> contents = params.allSimple();
        int x1 = MapUtils.getIntValue(contents, "x1");
        int y1 = MapUtils.getIntValue(contents, "y1");
        int x2 = MapUtils.getIntValue(contents, "x2");
        int y2 = MapUtils.getIntValue(contents, "y2");
        int w = MapUtils.getIntValue(contents, "w");
        int h = MapUtils.getIntValue(contents, "h");
        Integer idUser = currentUserId();
        String jpgPath = Path.getHeadPhotoUploadPath(idUser);
        File jpegFile = new File(jpgPath);
        System.out.println(jpgPath);
        System.out.println(contents);
        if (jpegFile.exists()) {

            try {
                BufferedImage src = javax.imageio.ImageIO.read(jpegFile);
                BufferedImage dest = Scalr.crop(src, x1, y1, w, h, Scalr.OP_ANTIALIAS);
                if (w > 200) {
                    dest = Scalr.resize(dest, 200, Scalr.OP_ANTIALIAS);
                }
                String jpgPath1 = Path.getHeadPhotoUploadPath(idUser, 1);
                File jpegFile1 = new File(jpgPath1);
                ImageIO.write(dest, "JPEG", jpegFile1);
            } catch (IOException e) {
                Logger.error(e, "user photo crop error.");
            }

        }
        Done done = new Done(i18n("success"), Done.Status.Success);
        String path0 = Path.getHeadPhotoDownloadPath(idUser, 0);
        done.put("path0", path0);
        String path1 = Path.getHeadPhotoDownloadPath(idUser, 1);
        done.put("path1", path1);
        renderJSON(done);

    }

    /**
     * {"success": false, "error": "error message to display", "preventRetry": true}
     *
     * @param qqfile
     */
    public static void headUpload(File qqfile) {
        System.out.println(qqfile);
        System.out.println(params.allSimple());
        Done done = new Done(i18n("success"), Done.Status.Success);
        try {
            Integer idUser = currentUserId();

            String jpgPath = Path.getHeadPhotoUploadPath(idUser);
            File jpegFile = new File(jpgPath);
            if (jpegFile.exists()) {
                jpegFile.delete();
            } else {
                jpegFile.getParentFile().mkdirs();
            }

            BufferedImage src = javax.imageio.ImageIO.read(qqfile);
            int w = src.getWidth();
            int h = src.getHeight();
            System.out.printf("%d %d \n", w, h);
            if (w > 700 || h > 700) {
                BufferedImage dest = Scalr.resize(src, 700, 700, Scalr.OP_ANTIALIAS);

                ImageIO.write(dest, "JPEG", new File(jpgPath));

            } else {
                FileUtils.moveFile(qqfile, jpegFile);
            }
            String jpgPath1 = Path.getHeadPhotoUploadPath(idUser, 1);
            File jpegFile1 = new File(jpgPath1);
            if (jpegFile1.exists()) {
                jpegFile1.delete();
            }
            jpegProcess.cropAndResize(jpgPath, jpgPath1, 200);
            boolean updated = userService.updateHeadPhoto(idUser, true);
            done.put("isHeadPhoto", true);
            if (updated) {
                done.setStatus(Done.Status.Success);
                String path0 = Path.getHeadPhotoDownloadPath(idUser, 0);
                done.put("path0", path0);
                String path1 = Path.getHeadPhotoDownloadPath(idUser, 1);
                done.put("path1", path1);
            } else {
                done.setStatus(Done.Status.Fail);
            }

        } catch (Exception e) {
            done.setStatus(Done.Status.Error);
            Logger.error(e, "add profile error.");
        }

        renderJSON(done);

    }

    @Delete("/settings/camera/{id}/delete")
    public static void cameraDelete(long id) {
        try {

            profileRepository.deleteOneCamera(currentUserId(), id);
            Done done = new Done(i18n("success"), Done.Status.Success);
            renderJSON(done);

        } catch (Exception e) {
            renderErrorJSON();
            Logger.error(e, "delete camera error.");
            throw new JapidResult(new Equipment().render(new ArrayList<Camera>()));
        }

    }
}
