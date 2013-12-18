package controllers;

import cn.bran.play.JapidResult;
import fengfei.fir.model.PhotoShow;
import fengfei.ucm.entity.photo.Choice;
import fengfei.ucm.entity.photo.Refresh;
import fengfei.ucm.repository.ChoiceRepository;
import fengfei.ucm.repository.ShowRepository;
import fengfei.ucm.repository.impl.SqlChoiceRepository;
import fengfei.ucm.repository.impl.SqlShowRepository;
import japidviews.Application.admin.ChoiceView;
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
@With(Secure.class)
public class EditorChoice extends Admin {
    public static ShowRepository show = new SqlShowRepository();
    static ChoiceRepository choiceRepository = new SqlChoiceRepository();

    @Gets({@Get("/editor/choice/{<[0-9]+>pageNum}/?"), @Get("/editor/choice/?")})
    @Any("/editor/choice/{<[0-9]+>pageNum}/?")

    public static void choice(int pageNum) {
        String path = "/choice";
        try {
            Map<String, String> map = params.allSimple();
            Byte category = MapUtils.getByte(map, "c", null);
            pageNum = pageNum <= 0 ? 1 : pageNum;
            int offset = (pageNum - 1) * TotalRowShow;
            List<Refresh> refreshes = show.refreshed(category, offset, TotalRowShow);
            ShowAction.rankable(refreshes);

            throw new JapidResult(new ChoiceView().render(path, refreshes, pageNum));
        } catch (Exception e) {

            Logger.error(e, "show last photo error.");
            throw new JapidResult(new ChoiceView().render(
                    path,
                    new ArrayList<PhotoShow>(),
                    1));
        }
    }

    @Post("/editor/choiced")
    public static void choiced() throws Exception {
        Map<String, String> map = params.allSimple();
        long idPhoto = MapUtils.getLong(map, "id");
        String title = MapUtils.getString(map, "title");
        Integer idUser = currentUserId();
        String niceName = currentNiceName();
        int updateAt = (int) System.currentTimeMillis() / 1000;
        Choice choice = new Choice(idPhoto, title, idUser, niceName, updateAt);
        int updated = choiceRepository.addChoice(choice);
        renderDoneJSON(updated > 0);

    }

}
