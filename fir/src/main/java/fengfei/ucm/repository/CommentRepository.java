package fengfei.ucm.repository;

import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.photo.Comment;

import java.util.List;

public interface CommentRepository extends UnitNames {

    boolean addOne(Comment m) throws Exception;

    List<Comment> select(
        final long idPhoto,
        final Integer idUser,
        final int offset,
        final int limit) throws Exception;

    List<Comment> selectForSorted(long idPhoto, final Integer idUser, int offset, int limit)
        throws Exception;

}
