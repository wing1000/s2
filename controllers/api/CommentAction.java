package controllers.api;

import controllers.Admin;
import controllers.SecureForJson;
import fengfei.fir.model.Done;
import fengfei.ucm.entity.photo.Comment;
import fengfei.ucm.repository.CommentRepository;
import fengfei.ucm.repository.impl.SqlCommentRepository;
import fengfei.web.authority.Authority;
import fengfei.web.authority.Role;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import play.Logger;
import play.modules.router.Any;
import play.mvc.With;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Map;

/**
 * * 		/comment/done					CommentAction.commentDone
 * #*		/comments						CommentAction.showComments
 */

@Authority(role = Role.LoggedIn)
@Controller
public class CommentAction extends Admin {


    static CommentRepository repository = new SqlCommentRepository();

    @RequestMapping("/comment/done")
    public static Done commentDone(HttpServletRequest request) {
        int idUser = currentUserId();
        String niceName = currentNiceName();
        Map<String, String> map = escapeAllSimple(request);
        long idPhoto = MapUtils.getLongValue(map, "id_photo");
        long idParent = MapUtils.getLongValue(map, "id_parent");
        String content = request.getParameter("comment");
        String ip = request.getRemoteAddr();
        long current = System.currentTimeMillis();
        int createAt = (int) (current / 1000);
        Timestamp createAtGmt = new Timestamp(current);
        int idUserReply = MapUtils.getIntValue(map, "idUserReply");
        Done done=new Done();

        try {

            validation.maxSize(content, 128);
            renderHasErrors();
            content = StringEscapeUtils.escapeHtml4(content);

            Comment comment = new Comment(
                    idPhoto,
                    idUser,
                    niceName,
                    content,
                    ip,
                    createAt,
                    createAtGmt,
                    idUserReply,
                    idParent);

            boolean updated = repository.addOne(comment);
            renderDoneJSON(updated);
        } catch (Exception e) {
            Logger.error(e, "add comment error.");
            renderErrorJSON();
        }
    }

}
