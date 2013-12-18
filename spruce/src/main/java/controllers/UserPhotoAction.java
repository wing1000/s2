package controllers;

import cn.bran.play.JapidResult;
import fengfei.fir.model.PhotoShow;
import fengfei.ucm.entity.photo.Refresh;
import fengfei.ucm.repository.CommentRepository;
import fengfei.ucm.repository.PhotoRepository;
import fengfei.ucm.repository.ShowRepository;
import fengfei.ucm.repository.impl.SqlCommentRepository;
import fengfei.ucm.repository.impl.SqlPhotoRepository;
import fengfei.ucm.repository.impl.SqlShowRepository;
import fengfei.ucm.service.ReadFollowService;
import japidviews.Application.photo.Group;
import japidviews.Application.photo.Views;
import org.apache.commons.collections.MapUtils;
import play.Logger;
import play.modules.router.Any;
import play.modules.router.Get;
import play.modules.router.Gets;
import play.mvc.With;

import java.util.*;

@With(Secure.class)
public class UserPhotoAction extends Admin {

    public static ShowRepository show = new SqlShowRepository();
    public static CommentRepository comment = new SqlCommentRepository();
    static PhotoRepository photoRepository = new SqlPhotoRepository();
    public static ReadFollowService readFollowService = null;

    @Gets({@Get("/show/following/{<[0-9]+>pageNum}/?"), @Get("/show/following/?")})
    @Any("/show/following/{<[0-9]+>pageNum}/?")
    public static void following(int pageNum) {
        String path = "/show/following";
        try {
            Map<String, String> map = params.allSimple();
            Byte category = MapUtils.getByte(map, "c");
            System.out.println(pageNum);
            pageNum = pageNum <= 0 ? 1 : pageNum;
            int offset = (pageNum - 1) * TotalRowShow + 1;

            Set<Long> ids = new HashSet<>();// .find(offset, count);

            List<Refresh> refreshs = show.refreshed(category, offset, TotalRowShow);
            // for (Refresh refresh : rfs) {
            // ids.add(refresh.idPhoto);
            // }

            throw new JapidResult(new Views().render(pathTitle, path, refreshs, pageNum));
        } catch (Exception e) {

            Logger.error(e, "show following photo error.");
            throw new JapidResult(new Views().render(pathTitle, path, new ArrayList<PhotoShow>(), 1));
        }

    }

    @Any("/group")
    public static void group() {
        throw new JapidResult(new Group().render());
    }

}
