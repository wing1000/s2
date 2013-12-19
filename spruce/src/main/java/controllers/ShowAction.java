package controllers;

import fengfei.fir.model.PhotoShow;
import fengfei.fir.utils.Path;
import fengfei.spruce.cache.SimpleCache;
import fengfei.spruce.utils.DateTimeUtils;
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
import fengfei.web.authority.Authority;
import fengfei.web.authority.Role;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@Authority(role = Role.LoggedIn)
public class ShowAction extends Admin {
    static Logger logger = LoggerFactory.getLogger(ShowAction.class);

    static CommentRepository repository = new SqlCommentRepository();
    static UserRepository userRepository = new SqlUserRepository();
    public static ShowRepository show = new SqlShowRepository();
    public static CommentRepository comment = new SqlCommentRepository();
    static PhotoRepository photoRepository = new SqlPhotoRepository();
    public static ReadFollowService readFollowService = FollowServiceUtils.readFollowService;

    @RequestMapping("/show/{<[0-9]+>idPhoto}_{<[0-9]+>photoIdUser}/?")
    public ModelAndView show(long idPhoto, Integer photoIdUser, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("photo/Show");

        String ip = request.getRemoteAddr();
        System.out.println(idPhoto);
        Integer idUser = currentUserId();
        String username = currentNiceName();
        int iip = getIntRemoteIP();
        Photo photo = photoRepository.view(idPhoto, photoIdUser, idUser, iip);

        if (photo == null) {// 输入错误的ID,或者照片被删除，或者不存在
            logger.info("photo is no-exists for id " + idPhoto);
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

        mv.addObject("photo", photo);
        mv.addObject("rank", photo.rank);
        mv.addObject("exif", exif);
        mv.addObject("isFollow", isFollow);
        mv.addObject("isFavorite", isFavorite);
        mv.addObject("isVote", isVote);
        return mv;

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
    public ModelAndView showRank(long id) throws Exception {
        ModelAndView mv = new ModelAndView("photo/RankShow");
        Rank rank = photoRepository.getRank(id);
        mv.addObject("rank", rank);
        return mv;
    }

    @RequestMapping("/comments")
    public void showComments(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("photo/Comments");
        Map<String, String> map = allSimple(request);
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
        mv.addObject("comments", comments);
        mv.addObject("pages", pages);
        mv.addObject("cp", cp);
        mv.addObject("maxPage", maxPage);
    }

    @RequestMapping("/last/?")
    public ModelAndView lastFirst(int pageNum) throws Exception {
        return last(0);
    }

    @RequestMapping("/last/{<[0-9]+>pageNum}/?")
    public ModelAndView last(int pageNum) throws Exception {
        ModelAndView mv = new ModelAndView("photo/Show");
        String path = "/last";
        Map<String, String> map = allSimple();
        Byte category = MapUtils.getByte(map, "c", null);
        pageNum = pageNum <= 0 ? 1 : pageNum;
        int offset = (pageNum - 1) * TotalRowShow;
        List<Refresh> refreshes = show.refreshed(category, offset, TotalRowShow);
        // for (Refresh refresh : rfs) {
        // ids.add(refresh.idPhoto);
        // }
        rankable(refreshes);

        mv.addObject("pathTitle", pathTitle);
        mv.addObject("path", path);
        mv.addObject("refreshes", refreshes);
        mv.addObject("pageNum", pageNum);
        return mv;
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


    protected static List<? extends PhotoShow> action(
            String action,
            Integer idUser,
            int offset,
            int limit) throws Exception {
        int iip = getIntRemoteIP();
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
