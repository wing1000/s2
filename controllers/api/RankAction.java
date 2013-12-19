package controllers.api;

import controllers.Admin;
import controllers.SecureForJson;
import fengfei.fir.model.Done;
import fengfei.fir.model.Done.Status;
import fengfei.ucm.repository.PhotoRepository;
import fengfei.ucm.repository.impl.SqlPhotoRepository;
import org.apache.commons.collections.MapUtils;
import play.Logger;
import play.modules.router.Any;
import play.mvc.With;

import java.util.Map;

//@With(SecureForJson.class)
public class RankAction extends Admin {

    static PhotoRepository photoService = new SqlPhotoRepository();

    @RequestMapping("/vote/{id}")
    public static void vote(long id) {
        Integer idUser = currentUserId();
        String username = currentNiceName();
        Map<String, String> as = params.allSimple();
        byte category = MapUtils.getByteValue(as, "category");
        String photoNiceName = MapUtils.getString(as, "niceName");
        Integer photoIdUser = MapUtils.getInteger(as, "photoIdUser");
        try {
            int iip = getIIP();
            int updated = photoService.vote(id, photoIdUser, photoNiceName, category, idUser, iip);
            renderDoneJSON(updated > 0);
        } catch (Exception e) {
            Logger.error(e, "vote error.");
            renderErrorJSON();
        }
    }
    @RequestMapping("/favorite/{id}")
    public static void favorite(long id) {
        Integer idUser = currentUserId();
        String username = currentNiceName();
        Map<String, String> as = params.allSimple();
        byte category = MapUtils.getByteValue(as, "category");
        String photoNiceName = MapUtils.getString(as, "niceName");
        Integer photoIdUser = MapUtils.getInteger(as, "photoIdUser");
        try {
            int iip = getIIP();
            int updated = photoService.favorite(
                    id,
                    photoIdUser,
                    photoNiceName,
                    category,
                    idUser,
                    iip);
            renderDoneJSON(updated > 0);
        } catch (Exception e) {
            Logger.error(e, "favorite error.");
            renderErrorJSON();
        }
    }
    @RequestMapping("/unfavorite/{id}")
    public static void unFavorite(long id) {
        Integer idUser = currentUserId();
        Map<String, String> as = params.allSimple();
        byte category = MapUtils.getByteValue(as, "category");
        String photoNiceName = MapUtils.getString(as, "niceName");
        Integer photoIdUser = MapUtils.getInteger(as, "photoIdUser");
        System.out.println(as);
        try {
            int iip = getIIP();
            int updated = photoService.unfavorite(id, photoIdUser, idUser, iip);
            renderDoneJSON(updated > 0);
        } catch (Exception e) {
            Logger.error(e, "favorite error.");
            renderErrorJSON();
        }
    }
}
