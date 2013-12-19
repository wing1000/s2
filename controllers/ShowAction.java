package controllers;

import cn.bran.play.JapidResult;
import fengfei.fir.model.PhotoShow;
import fengfei.fir.utils.Path;
import fengfei.spruce.cache.SimpleCache;
import fengfei.spruce.utils.FollowServiceUtils;
import fengfei.sprucy.AppConstants;
import fengfei.ucm.entity.photo.*;
import fengfei.ucm.entity.profile.User;
import fengfei.ucm.repository.CommentRepository;
import fengfei.ucm.repository.PhotoRepository;
import fengfei.ucm.repository.ShowRepository;
import fengfei.ucm.repository.UserRepository;
import fengfei.ucm.repository.impl.SqlCommentRepository;
import fengfei.ucm.repository.impl.SqlPhotoRepository;
import fengfei.ucm.repository.impl.SqlShowRepository;
import fengfei.ucm.repository.impl.SqlUserRepository;
import fengfei.ucm.service.ReadFollowService;
import japidviews.Application.Index;
import japidviews.Application.error.E500;
import japidviews.Application.photo.*;
import org.apache.commons.collections.MapUtils;
import play.Logger;
import play.modules.router.Any;
import play.modules.router.Get;
import play.modules.router.Gets;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class ShowAction extends Admin {

    static CommentRepository repository = new SqlCommentRepository();
    static UserRepository userRepository = new SqlUserRepository();
    public static ShowRepository show = new SqlShowRepository();
    public static CommentRepository comment = new SqlCommentRepository();
    static PhotoRepository photoRepository = new SqlPhotoRepository();
    public static ReadFollowService readFollowService = FollowServiceUtils.readFollowService;

    @RequestMapping("/show/{<[0-9]+>idPhoto}_{<[0-9]+>photoIdUser}/?")
    public static void show(long idPhoto, Integer photoIdUser) {

        try {
            String ip = request.remoteAddress;
            System.out.println(idPhoto);
            Integer idUser = currentUserId();
            String username = currentNiceName();
            int iip = getIIP();
            Photo photo = photoRepository.view(idPhoto, photoIdUser, idUser, iip);

            if (photo == null) {// 输入错误的ID,或者照片被删除，或者不存在
                Logger.info("photo is no-exists for id " + idPhoto);
                render500();
            }
            photo.niceName = photo.user.niceName;
            Map<String, String> exif = toMap(photo);
            User user = photo.user;
            user.headPath = Path.getHeadPhotoDownloadPath(user.idUser);

            boolean isFavorite = false;
            boolean isVote = false;
            boolean isFollow = false;
            // int followNum[] = { 0, 0 };

            isFavorite = photoRepository.isFavorite(idPhoto, idUser, iip);
            isVote = photoRepository.isVote(idPhoto, idUser, iip);
            if (idUser != null) {
                // 只有登陆
                isFollow = readFollowService.isFollow(null, idUser, photo.idUser, AppConstants.DefaultFollowType);
                // followNum[0] = readFollowService.countTarget(null, idUser);
                // followNum[1] = readFollowService.countSource(null, idUser);

            }
            System.out.println(isFollow);
            System.out.println(isVote);
            System.out.println(isFavorite);
            throw new JapidResult(new Show().render(
                    photo,
                    photo.rank,
                    exif,
                    isFollow,
                    isFavorite,
                    isVote));
        } catch (Exception e) {
            Logger.error(e, "show photo error.");
            throw new JapidResult(new E500().render());

        }
    }

    private static Map<String, String> toMap(Photo photo) {
        Map<String, String> exif = new LinkedHashMap<String, String>();
        put(exif, "make", photo.make);
        put(exif, "camera", photo.model);
        put(exif, "aperture", photo.aperture);
        put(exif, "shutter", photo.shutter);
        put(exif, "focus", photo.focus);
        put(exif, "iso", photo.iso);
        put(exif, "lens", photo.lens);
        put(exif, "ev", photo.ev);
        put(exif, "category", SimpleCache.categories.get(photo.category));
        put(exif, "taken", photo.dateTimeOriginal);

        return exif;
    }

    private static void put(Map<String, String> exif, String key, String value) {
        if (value != null && !"".equals(value)) {
            exif.put(key, value);
        }

    }

    @RequestMapping("/show/rank/{id}")
    public static void showRank(long id) {
        try {
            Rank rank = photoRepository.getRank(id);
            throw new JapidResult(new RankShow().render(rank));
        } catch (Exception e) {
            Logger.error(e, "show photo error.");
            throw new JapidResult(new RankShow().render(new Rank()));
        }
    }

    @RequestMapping("/comments")
    public static void showComments() {
        try {
            Map<String, String> map = params.allSimple();
            long idPhoto = MapUtils.getLongValue(map, "id");
            Integer idUser = MapUtils.getInteger(map, "id_user");

            int page = MapUtils.getIntValue(map, "p");
            int cp = MapUtils.getIntValue(map, "cp");
            int total = MapUtils.getIntValue(map, "t");
            System.out.println(cp + "," + page);
            cp += page;
            cp = cp <= 0 ? 1 : cp;
            List<Comment> comments = repository.select(idPhoto, idUser, (cp - 1) * Row, Row);
            //Set<Integer> idUsers = new HashSet<>();
            for (Comment comment : comments) {
                //idUsers.add(comment.idUser);

                comment.sinceTime = DateTimeUtils.period(comment.createAtGmt.getTime());
            }

            int maxPage = total / Row + 1;
            List<String> pages = pageList(cp, maxPage);
            System.out.println(pages);
            throw new JapidResult(new Comments().render(comments, pages, cp, maxPage));
        } catch (Exception e) {
            Logger.error(e, "show photo error.");
        }
    }

    public static void showUser(String id) {
        throw new JapidResult(new Index().render());
    }

    @Gets({@Get("/choice/{<[0-9]+>pageNum}/?"), @Get("/choice/?")})
    @RequestMapping("/choice/{<[0-9]+>pageNum}/?")
    public static void choice(int pageNum) {
        String path = "/choice";
        try {
            Map<String, String> map = params.allSimple();
            Byte category = MapUtils.getByte(map, "c", null);
            pageNum = pageNum <= 0 ? 1 : pageNum;
            int offset = (pageNum - 1) * TotalRowShow;
            List<Choice> choices = show.choiced(category, offset, TotalRowShow);

            rankable(choices);

            throw new JapidResult(new Views().render(pathTitle, path, choices, pageNum));
        } catch (Exception e) {

            Logger.error(e, "show last photo error.");
            throw new JapidResult(new Views().render(

                    pathTitle, path, new ArrayList<PhotoShow>(), 1));
        }
    }

    @Gets({@Get("/last/{<[0-9]+>pageNum}/?"), @Get("/last/?")})
    @RequestMapping("/last/{<[0-9]+>pageNum}/?")
    public static void last(int pageNum) {
        String path = "/last";
        try {
            Map<String, String> map = params.allSimple();
            Byte category = MapUtils.getByte(map, "c", null);
            pageNum = pageNum <= 0 ? 1 : pageNum;
            int offset = (pageNum - 1) * TotalRowShow;
            List<Refresh> refreshs = show.refreshed(category, offset, TotalRowShow);
            // for (Refresh refresh : rfs) {
            // ids.add(refresh.idPhoto);
            // }
            rankable(refreshs);

            throw new JapidResult(new Views().render(pathTitle, path, refreshs, pageNum));
        } catch (Exception e) {

            Logger.error(e, "show last photo error.");
            throw new JapidResult(new Views().render(
                    pathTitle,
                    path,
                    new ArrayList<PhotoShow>(),
                    1));
        }
    }


    static <T extends PhotoShow> void rankable(List<T> shows) throws Exception {

        if (shows != null && shows.size() > 0) {
            List<Long> ids = new ArrayList<>();
            for (PhotoShow photoShow : shows) {
                ids.add(photoShow.idPhoto);
            }

            List<Rank> ranks = show.selectRanks(ids);
            Map<Long, String> scores = toScore(ranks);
            for (PhotoShow photoShow : shows) {
                photoShow.sscore = scores.get(photoShow.idPhoto);
            }
        }
    }

    public static Map<Long, String> toScore(List<Rank> ranks) {
        Map<Long, String> map = new HashMap<>();
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        for (Rank rank : ranks) {
            map.put(rank.idPhoto, df.format(rank.score));
        }
        return map;

    }

    @Gets({@Get("/pop/{<[0-9]+>pageNum}/?"), @Get("/pop/?"), @Get("/")})
    @RequestMapping("/pop/{<[0-9]+>pageNum}/?")
    public static void popular(int pageNum) {
        String path = "/pop";
        try {
            Map<String, String> map = params.allSimple();
            Byte category = MapUtils.getByte(map, "c", null);
            pageNum = pageNum <= 0 ? 1 : pageNum;
            int offset = (pageNum - 1) * TotalRowShow;

            // List<Rank> ranks = show.pop(offset, TotalRowShow);
            List<Rank> populars = show.popular(category, offset, TotalRowShow);
            // rankable(populars);
            throw new JapidResult(new Views().render(pathTitle, path, populars, pageNum));
        } catch (Exception e) {

            Logger.error(e, "show pop photo error.");
            throw new JapidResult(new Views().render(
                    pathTitle,
                    path,
                    new ArrayList<PhotoShow>(),
                    1));
        }
    }

    @Gets({@Get("/upcoming/{<[0-9]+>pageNum}/?"), @Get("/upcoming/?")})
    @RequestMapping("/upcoming/{<[0-9]+>pageNum}/?")
    public static void upcoming(int pageNum) {
        String path = "/upcoming";
        try {
            Map<String, String> map = params.allSimple();
            Byte category = MapUtils.getByte(map, "c", null);
            pageNum = pageNum <= 0 ? 1 : pageNum;
            int offset = (pageNum - 1) * TotalRowShow;
            List<Rank> upcomings = show.upcomed(category, offset, TotalRowShow);
            // rankable(upcomings);
            throw new JapidResult(new Views().render(pathTitle, path, upcomings, pageNum));
        } catch (Exception e) {
            Logger.error(e, "show pop photo error.");
            throw new JapidResult(new Views().render(
                    pathTitle,
                    path,
                    new ArrayList<PhotoShow>(),
                    1));
        }
    }

    @Gets({@Get("/category/{<[0-9]+>pageNum}/?"), @Get("/category/?")})
    @RequestMapping("/category/{<[0-9]+>pageNum}/?")
    public static void catalog(int pageNum) {
        String path = "/category";
        try {
            Map<String, String> map = params.allSimple();
            Byte category = MapUtils.getByte(map, "c", null);
            pageNum = pageNum <= 0 ? 1 : pageNum;
            int offset = (pageNum - 1) * TotalRowShow;
            List<Refresh> refreshs = show.categorized(category, offset, TotalRowShow);
            throw new JapidResult(new Category().render(pathTitle, path, refreshs, pageNum));
        } catch (Exception e) {
            Logger.error(e, "show pop photo error.");
            throw new JapidResult(new Category().render(
                    pathTitle,
                    path,
                    new ArrayList<PhotoShow>(),
                    1));
        }
    }

    // home actions
    @Gets({@Get("/flow/{<[0-9]+>pageNum}/?"), @Get("/flow/?")})
    @RequestMapping("/flow/{<[0-9]+>pageNum}/?")
    public static void flow(int pageNum) {
        String path = "/flow";
        int iip = getIIP();

        try {
            pageNum = pageNum <= 0 ? 1 : pageNum;
            int offset = (pageNum - 1) * TotalRowShow;
            List<PhotoAccess> accesses = show.selectPhotoViewsByIP(iip, offset, TotalRowShow);
            // for (Refresh refresh : rfs) {
            // ids.add(refresh.idPhoto);
            // }
            // rankable(accesses);
            throw new JapidResult(new HomeViews().render(pathTitle, path, accesses, pageNum));
        } catch (Exception e) {

            Logger.error(e, "show last photo error.");
            throw new JapidResult(new HomeViews().render(
                    pathTitle,
                    path,
                    new ArrayList<PhotoShow>(),
                    1));
        }
    }

    protected static List<? extends PhotoShow> action(
            String action,
            Integer idUser,
            int offset,
            int limit) throws Exception {
        int iip = getIIP();
        switch (action) {
            case "fav":
                List<Favorite> favorites = show.selectFavoritesByIP(iip, offset, limit);
                return favorites;
            case "flow":
                List<PhotoAccess> accesses = show.selectPhotoViewsByIP(iip, offset, limit);
                return accesses;
            default:
                accesses = show.selectPhotoViewsByIP(iip, offset, limit);
                return accesses;
        }
    }

    @Gets({@Get("/fav/{<[0-9]+>pageNum}/?"), @Get("/fav/?")})
    @RequestMapping("/fav/{<[0-9]+>pageNum}/?")
    public static void fav(int pageNum) {
        String path = "/fav";
        try {
            Map<String, String> map = params.allSimple();
            Byte category = MapUtils.getByte(map, "c", null);
            pageNum = pageNum <= 0 ? 1 : pageNum;
            int offset = (pageNum - 1) * TotalRowShow;
            int iip = getIIP();
            List<Favorite> favorites = show.selectFavoritesByIP(iip, offset, TotalRowShow);

            throw new JapidResult(new HomeViews().render(pathTitle, path, favorites, pageNum));
        } catch (Exception e) {
            Logger.error(e, "show pop photo error.");
            throw new JapidResult(new HomeViews().render(
                    pathTitle,
                    path,
                    new ArrayList<PhotoShow>(),
                    1));
        }
    }

    public static void activity() {

    }

    public static enum PageOperation {
        None,
        First,
        Previous,
        Next,
        Last,
    }

}
