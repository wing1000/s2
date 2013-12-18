package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CommentTransducer implements Transducer<Comment> {

    @Override
    public Comment transform(ResultSet rs) throws SQLException {

        long idPhoto = rs.getLong("id_photo");
        long idComment = rs.getLong("id_comment");
        long commentPhoto = rs.getLong("comment_photo");
        int idUser = rs.getInt("id_user");
        String niceName = rs.getString("user_nice_name");
        String content = rs.getString("content");
        String ip = rs.getString("ip");
        int idUserReply = rs.getInt("id_user_reply");
        int idParent = rs.getInt("id_parent");
        int status = rs.getInt("status");

        int createAt = rs.getInt("create_at");
        Timestamp createAtGmt = rs.getTimestamp("create_at_gmt");

        //

        Comment comment = new Comment(
                idComment,
                idPhoto,
                idUser,
                niceName,
                content,
                ip,
                createAt,
                createAtGmt,
                idUserReply,
                idParent);
        comment.commentPhoto =commentPhoto;
        comment.setStatus(status);
        return comment;
    }
}
