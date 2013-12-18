package controllers.api;

import controllers.Admin;
import controllers.SecureForJson;
import fengfei.ucm.entity.photo.Comment;
import fengfei.ucm.repository.CommentRepository;
import fengfei.ucm.repository.impl.SqlCommentRepository;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import play.Logger;
import play.modules.router.Any;
import play.mvc.With;

import java.sql.Timestamp;
import java.util.Map;

/**
 * * 		/comment/done					CommentAction.commentDone
 * #*		/comments						CommentAction.showComments
 */
@With(SecureForJson.class)
public class CommentAction extends Admin {


    static CommentRepository repository = new SqlCommentRepository();

    @Any("/comment/done")
    public static void commentDone() {
        int idUser = currentUserId();
        String niceName = currentNiceName();
        Map<String, String> map = escapeAllSimple();
        long idPhoto = MapUtils.getLongValue(map, "id_photo");
        long idParent = MapUtils.getLongValue(map, "id_parent");
        String content = params.get("comment");
        String ip = request.remoteAddress;
        long current = System.currentTimeMillis();
        int createAt = (int) (current / 1000);
        Timestamp createAtGmt = new Timestamp(current);
        int idUserReply = MapUtils.getIntValue(map, "idUserReply");

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
