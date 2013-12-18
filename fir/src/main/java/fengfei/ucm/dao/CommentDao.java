package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.ucm.dao.transducer.CommentTransducer;
import fengfei.ucm.entity.photo.Comment;

import java.sql.SQLException;
import java.util.List;

public class CommentDao {

    final static String Insert = "INSERT INTO photo_comments%s(id_comment,id_photo, content, id_user, user_nice_name,ip, create_at, "
            + "create_at_gmt, status, id_user_reply, id_parent,comment_photo)"
            + "VALUES (?,?,?,?,?,?,?,?,?,?)";

    public static int addOne(ForestGrower grower, String suffix, Comment m) throws SQLException {

        String insert = String.format(Insert, suffix);
        int updated = grower.update(
                insert,
                m.idComment,
                m.idPhoto,
                m.content,
                m.idUser,
                m.niceName,
                m.ip,
                m.createAt,
                m.createAtGmt,
                Comment.Approved,
                m.idUserReply,
                m.idParent, m.commentPhoto);
        return updated;

    }

    final static String UpdateStatus = "update photo_comments%s set status=? where id_comment=?";

    public static int updateStatus(ForestGrower grower, String suffix, Long idComment, int status) throws SQLException {

        String insert = String.format(UpdateStatus, suffix);
        int updated = grower.update(insert, status, idComment);
        return updated;

    }

    final static String Select = "SELECT id_comment, id_photo, content, id_user,user_nice_name, ip, create_at, create_at_gmt,  status, id_user_reply, id_parent,comment_photo "
            + " FROM photo_comments%s where id_photo=? and id_user=? ORDER BY id_comment desc  limit ?,?";

    public static List<Comment> select(
            ForestGrower grower,
            String suffix,
            long idPhoto,
            Integer idUser,
            int offset,
            int limit) throws SQLException {
        List<Comment> comments = grower.select(
                String.format(Select, suffix),
                new CommentTransducer(),
                idPhoto,
                idUser,
                offset,
                limit);
        return comments;
    }
}
