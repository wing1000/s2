package controllers;

import cn.bran.play.JapidResult;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import fengfei.fir.model.PhotoShow;
import fengfei.fir.utils.Path;
import fengfei.spruce.utils.FollowServiceUtils;
import fengfei.sprucy.AppConstants;
import fengfei.ucm.entity.photo.Favorite;
import fengfei.ucm.entity.photo.Photo;
import fengfei.ucm.entity.photo.PhotoAccess;
import fengfei.ucm.entity.photo.Rank;
import fengfei.ucm.entity.profile.Camera;
import fengfei.ucm.entity.profile.User;
import fengfei.ucm.repository.*;
import fengfei.ucm.repository.impl.*;
import fengfei.ucm.service.ReadFollowService;
import fengfei.ucm.service.WriteFollowService;
import japidviews.Application.photo.Edit;
import japidviews.Application.photo.UserViews;
import japidviews.Application.photo.Yours;
import play.Logger;
import play.modules.router.Any;
import play.modules.router.Get;
import play.modules.router.Gets;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YouShowAction extends Admin {

    public static ShowRepository show = new SqlShowRepository();
    static UserRepository userRepository = new SqlUserRepository();
    static ProfileRepository profileRepository = new SqlProfileRepository();
    public static CommentRepository comment = new SqlCommentRepository();
    static PhotoRepository photoRepository = new SqlPhotoRepository();
    public static WriteFollowService writeFollowService = FollowServiceUtils.writeFollowService;
    public static ReadFollowService readFollowService = FollowServiceUtils.readFollowService;
    public final static int TotalRowLimit = 15;

    protected static List<? extends PhotoShow> action(
        String action,
        Integer idUser,
        int offset,
        int limit) throws Exception {

        switch (action) {
        case "home":
            List<Rank> ranks = show.selectUserPhotos(idUser, offset, limit);
            return ranks;
        case "fav":
            List<Favorite> favorites = show.selectFavorites(idUser, offset, limit);
            return favorites;
        case "flow":
            List<PhotoAccess> accesses = show.selectPhotoAccesses(idUser, offset, limit);
            return accesses;
        default:
            ranks = show.selectUserPhotos(idUser, offset, limit);
            return ranks;
        }
    }

    @Gets({ @Get("/yours/{<[0-9]+>pageNum}/{action}/?"), @Get("/yours/{action}/?"),
            @Get("/yours/?") })
    @Any("/yours/{<[0-9]+>pageNum}/{action}/?")
    public static void your(int pageNum, String action) {
        String path = "/yours";
        if (action == null) {
            action = "home";
        }
        Integer idUser = currentUserId();
        User user = null;
        try {
            user = userRepository.getFullUser(idUser);
            pageNum = pageNum <= 0 ? 1 : pageNum;
            int offset = (pageNum - 1) * TotalRowLimit;

            //
            Rank rank = show.getUserRank(idUser);
            if (rank == null) {
                rank = new Rank();
            }

            //

            List<Long> targets = readFollowService.findTargets(null, idUser, AppConstants.DefaultFollowType);
            List<Long> sources = readFollowService.findSources(null, idUser,AppConstants.DefaultFollowType);
            int tc = readFollowService.countTarget(null, idUser,AppConstants.DefaultFollowType);
            int sc = readFollowService.countSource(null, idUser,AppConstants.DefaultFollowType);

            List<PhotoShow> photos = (List<PhotoShow>) action(
                action,
                idUser,
                offset,
                TotalRowLimit);
            throw new JapidResult(new Yours().render(
                path,
                photos,
                pageNum,
                user,
                targets,
                sources,
                tc,
                sc,
                rank));
        } catch (Exception e) {
            Logger.error(e, "show pop photo error.");
            throw new JapidResult(new Yours().render(
                path,
                new ArrayList<PhotoShow>(),
                1,
                user,
                new ArrayList<Long>(),
                new ArrayList<Long>(),
                0,
                0,
                new Rank()));
        }
    }

    protected static void toUserShow(User user, int pageNum, String action) {
        if (action == null) {
            action = "home";
        }
        String path = "/to/";
        try {
            System.out.println(user);
            Integer idUser = user.idUser;
            pageNum = pageNum <= 0 ? 1 : pageNum;
            int offset = (pageNum - 1) * TotalRowLimit;

            //
            ListMultimap<String, Camera> cameras = profileRepository.selectCamerasGroup(idUser);
            List<PhotoShow> photos = (List<PhotoShow>) action(
                action,
                idUser,
                offset,
                TotalRowLimit);
            //
            Rank rank = show.getUserRank(idUser);
            if (rank == null) {
                rank = new Rank();
            }
            //
            List<Long> targets = readFollowService.findTargets(null, idUser,AppConstants.DefaultFollowType);
            List<Long> sources = readFollowService.findSources(null, idUser,AppConstants.DefaultFollowType);
            int tc = readFollowService.countTarget(null, idUser,AppConstants.DefaultFollowType);
            int sc = readFollowService.countSource(null, idUser,AppConstants.DefaultFollowType);
            // List<User> tUsers = userRepository.selectUserList(targets);
            // List<User> sUsers = userRepository.selectUserList(sources);
            boolean isFollow = false;
            Integer loginIdUser = currentUserId();
            if (loginIdUser != null) {
                // 只有登陆
                isFollow = readFollowService.isFollow(null, loginIdUser, user.idUser,AppConstants.DefaultFollowType);
            }
            throw new JapidResult(new UserViews().render(
                path,
                photos,
                pageNum,
                user,
                targets,
                sources,
                tc,
                sc,
                rank,
                cameras,
                isFollow,
                action));
        } catch (Exception e) {
            Logger.error(e, "show user home error.");
            ListMultimap<String, Camera> multimap = ArrayListMultimap.create();
            throw new JapidResult(new UserViews().render(
                path,
                new ArrayList<PhotoShow>(),
                1,
                new User(),
                new ArrayList<Long>(),
                new ArrayList<Long>(),
                0,
                0,
                new Rank(),
                multimap,
                false,
                "home"));
        }
    }

    @Gets({ @Get("/to/{idUser}/{<[0-9]+>pageNum}/{action}/?"), @Get("/to/{idUser}/{action}/?"),
            @Get("/to/{idUser}/?") })
    @Any(value = "/to/{idUser}/{<[0-9]+>pageNum}/{action}/?", priority = 200)
    public static void userShowByID(Integer idUser, int pageNum, String action) {
        try {
            User user = userRepository.getFullUser(idUser);
            if (user == null) {

                render500();
            }
            toUserShow(user, pageNum, action);
        } catch (Exception e) {
            Logger.error(e, "show user home error.");
            render500();
        }
    }

    @Gets({ @Get(value = "/u/{username}/{<[0-9]+>pageNum}/{action}/?", priority = 100),
            @Get(value = "/u/{username}/{action}/?", priority = 120) })
    @Any(value = "/u/{username}/{<[0-9]+>pageNum}/{action}/?", priority = 200)
    public static void userShowByName(String username, int pageNum, String action) {

        try {
            User user = userRepository.getUserByUserName(username);
            if (user == null) {
                render500();
            }
            toUserShow(user, pageNum, action);
        } catch (Exception e) {
            Logger.error(e, "show user home error.");
            render500();
        }
    }

    // @Any("/edit/{<[0-9]+>id}/?")
    public static void edit(String id) {

        try {
            String ip = request.remoteAddress;
            System.out.println(id);
            Integer idUser = currentUserId();
            String username = currentNiceName();
            long idPhoto = Long.parseLong(id);
            int iip = getIIP();
            Photo photo = photoRepository.view(idPhoto, idUser, idUser, iip);
            Map<String, String> exif = toMap(photo);
            User user = photo.user;
            user.headPath = Path.getHeadPhotoDownloadPath(user.idUser);
            if (user.niceName == null || "".equals(user.niceName)) {
                user.niceName = user.realName;
            }
            if (user.niceName == null || "".equals(user.niceName)) {
                user.niceName = user.userName;
            }

            throw new JapidResult(new Edit().render(photo, photo.rank, exif));
        } catch (Exception e) {
            Logger.error(e, "show photo error.");
            renderText("500");
        }
    }

    private static Map<String, String> toMap(Photo photo) {
        Map<String, String> exif = new LinkedHashMap<String, String>();
        put(exif, "Make", photo.make);
        put(exif, "Camera", photo.model);
        put(exif, "Aperture", photo.aperture);
        put(exif, "Shutter Speed", photo.shutter);
        put(exif, "Focal Length", photo.focus);
        put(exif, "ISO", photo.iso);
        put(exif, "Lens", photo.lens);
        put(exif, "Ev", photo.ev);
        put(exif, "Taken at", photo.dateTimeOriginal);

        return exif;
    }

    private static void put(Map<String, String> exif, String key, String value) {
        if (value != null && !"".equals(value)) {
            exif.put(key, value);
        }

    }

    public static void follow(String toid) {
        long sourceId = currentUserId();
        try {
            boolean followed = writeFollowService.add(null, sourceId, Long.parseLong(toid),AppConstants.DefaultFollowType);
            renderDoneJSON(followed);
        } catch (Exception e) {
            Logger.error(e, "follow error.");
            renderJSON("{success:false}");
        }

    }

    public static void unfollow(String toid) {
        long sourceId = currentUserId();
        try {
            boolean followed = writeFollowService.remove(null, sourceId, Long.parseLong(toid),AppConstants.DefaultFollowType);
            renderDoneJSON(followed);
        } catch (Exception e) {
            Logger.error(e, "unfollow error.");
            renderJSON("{success:false}");
        }

    }

    public static void countFollows() {
        long sourceId = currentUserId();
        try {
            int follow[] = readFollowService.count(null, sourceId,AppConstants.DefaultFollowType);
            renderJSON("{following:" + follow[0] + ",followed:" + follow[1] + "}");
        } catch (Exception e) {
            Logger.error(e, "count follow error.");
            renderJSON("{success:false}");
        }
    }

}
