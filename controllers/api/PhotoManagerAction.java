package controllers.api;

import cn.bran.play.JapidResult;
import controllers.Admin;
import controllers.SecureForJson;
import fengfei.fir.model.PhotoShow;
import fengfei.sprucy.AppConstants;
import fengfei.ucm.entity.photo.PhotoSet;
import fengfei.ucm.entity.photo.Rank;
import fengfei.ucm.repository.PhotoManageRepository;
import fengfei.ucm.repository.PhotoRepository;
import fengfei.ucm.repository.ShowRepository;
import fengfei.ucm.repository.impl.SqlPhotoManageRepository;
import fengfei.ucm.repository.impl.SqlPhotoRepository;
import fengfei.ucm.repository.impl.SqlShowRepository;
import japidviews.Application.profile.Dir;
import japidviews.Application.profile.DirNav;
import japidviews.Application.profile.DirReOrg;
import japidviews.Application.profile.PhotoList;
import org.apache.commons.collections.MapUtils;
import play.Logger;
import play.modules.router.Any;
import play.modules.router.Get;
import play.modules.router.Gets;
import play.modules.router.Post;
import play.mvc.With;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@With(SecureForJson.class)
public class PhotoManagerAction extends Admin {

    public final static int TotalRowLimit = 15;
    static ShowRepository show = new SqlShowRepository();
    static PhotoManageRepository photoManage = new SqlPhotoManageRepository();
    static PhotoRepository photoRepository = new SqlPhotoRepository();

    @Gets({@Get("/photo/manage/all/{<[0-9]+>pageNum}/?"),
            @Get("/photo/manage/all/?")})
    @RequestMapping("/photo/manage/all/{<[0-9]+>pageNum}/?")
    public static void allPhoto(int pageNum) {
        Integer idUser = currentUserId();

        pageNum = pageNum <= 0 ? 1 : pageNum;
        int offset = (pageNum - 1) * TotalRowLimit;
        try {
            List<Rank> ranks = show.selectUserPhotos(idUser, offset,
                    TotalRowLimit);
            throw new JapidResult(new PhotoList().render("/photo/manage/all",
                    ranks, pageNum));
        } catch (Exception e) {
            throw new JapidResult(new PhotoList().render("/photo/manage/all",
                    new ArrayList<PhotoShow>(), pageNum));
        }
    }

    @Gets({@Get("/photo/manage/public/{<[0-9]+>pageNum}/?"),
            @Get("/photo/manage/public/?")})
    @RequestMapping("/photo/manage/public/{<[0-9]+>pageNum}/?")
    public static void allPhotoPublic(int pageNum) {
        Integer idUser = currentUserId();

        pageNum = pageNum <= 0 ? 1 : pageNum;
        int offset = (pageNum - 1) * TotalRowLimit;
        try {
            List<Rank> ranks = show.selectUserPhotos(idUser, offset,
                    TotalRowLimit);
            throw new JapidResult(new PhotoList().render(
                    "/photo/manage/public", ranks, pageNum));
        } catch (Exception e) {
            throw new JapidResult(
                    new PhotoList().render("/photo/manage/public",
                            new ArrayList<PhotoShow>(), pageNum));
        }
    }

    @RequestMapping("/photo/manage/dir/all")
    public static void allDirectory() {
        Integer idUser = currentUserId();
        try {
            Map<String, String> contents = params.allSimple();
            List<PhotoSet> photoSets = photoManage.selectUserSets(idUser);
            throw new JapidResult(new Dir().render(photoSets));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JapidResult(new Dir().render(new ArrayList<PhotoSet>()));

        }
    }

    @RequestMapping("/photo/manage/dir/nav")
    public static void directoryNav() {
        Integer idUser = currentUserId();
        try {
            List<PhotoSet> photoSets = photoManage.selectUserSets(idUser);
            throw new JapidResult(new DirNav().render(photoSets));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JapidResult(
                    new DirNav().render(new ArrayList<PhotoSet>()));

        }
    }

    @RequestMapping("/photo/org")
    public static void directoryOrganize() {

        Integer idUser = currentUserId();
        try {
            List<PhotoSet> photoSets = photoManage.selectUserSets(idUser);
            throw new JapidResult(new DirReOrg().render(photoSets));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JapidResult(
                    new DirReOrg().render(new ArrayList<PhotoSet>()));

        }
    }

    @Gets({@Get("/photo/manage/dir/{idSet}/{<[0-9]+>pageNum}/?"),
            @Get("/photo/manage/dir/{idSet}/?")})
    @RequestMapping("/photo/manage/dir/{idSet}/{<[0-9]+>pageNum}/?")
    public static void getPhotoByDirectory(long idSet, int pageNum) {
        String pagePath = "/photo/manage/set";
        Integer idUser = currentUserId();
        try {
            List<Long> photos = photoManage.selectSetPhoto(idSet, idUser);
            List<Rank> ranks = show.selectRanks(photos);
            throw new JapidResult(new PhotoList().render(pagePath, ranks, 0));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JapidResult(new PhotoList().render(pagePath,
                    new ArrayList<PhotoShow>(), pageNum));

        }
    }

    @Post("/photo/manage/dir/done")
    public static void directoryDone() {
        Integer idUser = currentUserId();
        try {
            Map<String, String> contents = params.allSimple();

            PhotoSet set = new PhotoSet();
            set.idSet = MapUtils.getLongValue(contents, "id", -1);
            set.idUser = idUser;
            set.name = MapUtils.getString(contents, "name");
            long curr = System.currentTimeMillis();

            set.createAt = (int) (curr / 1000);
            set.updateAt = curr;
            System.out.println(set);
            int updated = photoManage.saveSet(set);

            if (updated == 0) {
                flash.error(i18n("dir.save.error"));
            }
            if (updated == -1) {
                flash.error(i18n("dir.limit", AppConstants.DirLimit));
            }
            if (updated == -2) {
                flash.error(i18n("dir.name.existed", AppConstants.DirLimit));
            }

        } catch (Exception e) {
            flash.error(i18n("dir.save.error"));
            Logger.error(e, i18n("dir.save.error"));
        }
        allDirectory();
    }

    @Post("/photo/manage/dir/delete")
    public static void deleteSet() {
        Integer idUser = currentUserId();
        try {
            Map<String, String> contents = params.allSimple();
            Long idSet = MapUtils.getLong(contents, "id", null);
            System.out.println(idSet);
            if (idSet != null) {
                int updated = photoManage.deleteSet(idSet, idUser);
            }

        } catch (Exception e) {
            Logger.error(e, "delete set error.");
            e.printStackTrace();
        }
        allDirectory();
    }

    @Post("/photo/manage/dirs/done")
    public static void movePhotoToDirectory(long id) {
        Integer idUser = currentUserId();
        try {
            Map<String, String> contents = params.allSimple();
            long idPhoto = MapUtils.getLongValue(contents, "id_photo");
            long idSet = MapUtils.getLongValue(contents, "id_set");
            int updated = photoManage.addPhotoSets(idUser, idSet, idPhoto);
            renderDoneJSON(updated > 0);
        } catch (Exception e) {
            renderErrorJSON();
        }
    }

    @Post("/photo/manage/dirs/delete")
    public static void removePhotoFromDirectory() {
        Integer idUser = currentUserId();
        try {
            Map<String, String> contents = params.allSimple();
            long idPhoto = MapUtils.getLongValue(contents, "id_photo");
            int updated = photoManage.deletePhotoSets(idUser, idPhoto);
            renderDoneJSON(updated > 0);
        } catch (Exception e) {
            renderErrorJSON();
        }
    }

    @Post("/photo/{<[0-9]+>idPhoto}/delete")
    public static void deletePhoto(long idPhoto) {
        Integer idUser = currentUserId();
        try {
            boolean updated = photoRepository.deleteOne(idPhoto, idUser);
            System.out.println(updated);
            renderDoneJSON(updated);
        } catch (Exception e) {
            renderErrorJSON();
        }
    }
}
