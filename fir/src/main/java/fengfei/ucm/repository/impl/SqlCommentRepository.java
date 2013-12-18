package fengfei.ucm.repository.impl;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.slice.SliceResource.Function;
import fengfei.forest.slice.database.utils.Transactions;
import fengfei.forest.slice.database.utils.Transactions.TaCallback;
import fengfei.ucm.dao.CommentDao;
import fengfei.ucm.dao.PhotoDao;
import fengfei.ucm.dao.RankDao;
import fengfei.ucm.dao.Sequence;
import fengfei.ucm.entity.photo.Comment;
import fengfei.ucm.entity.photo.PhotoAccess.AccessType;
import fengfei.ucm.repository.CommentRepository;
import fengfei.ucm.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class SqlCommentRepository implements CommentRepository {
    public final static String CommentsTableName = "comments";
    public final static String CommentsPhotoTableName = "comments_photo";
    UserRepository userRepository = new SqlUserRepository();

    @Override
    public boolean addOne(final Comment m) throws Exception {


        long id = m.getIdUser();
        m.idComment = Sequence.next(CommentsTableName);
        int updated = Transactions.execute(
                PhotoUnitName,
                new Long(id),
                Function.Write,
                new TaCallback<Integer>() {

                    @Override
                    public Integer execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = "";
                        RankDao.updateRank(
                                grower,
                                suffix,
                                m.idPhoto,
                                m.idUser,
                                AccessType.Comment,
                                1);
                        PhotoDao.incrCommentCount(grower, suffix, m.idPhoto);
                        return CommentDao.addOne(grower, suffix, m);
                    }

                });

        return updated > 0;

    }

    @Override
    public List<Comment> select(
            final long idPhoto,
            final Integer idUser,
            final int offset,
            final int limit) throws Exception {


        List<Comment> cms = Transactions.execute(
                PhotoUnitName,
                new Long(idUser),
                Function.Read,
                new TaCallback<List<Comment>>() {

                    @Override
                    public List<Comment> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        return CommentDao.select(grower, suffix, idPhoto, idUser, offset, limit);
                    }

                });

        return cms;


    }

    @Override
    public List<Comment> selectForSorted(
            final long idPhoto,
            final Integer idUser,
            final int offset,
            final int limit) throws Exception {

        List<Comment> cms = Transactions.execute(
                PhotoUnitName,
                new Long(idPhoto),
                Function.Read,
                new TaCallback<List<Comment>>() {

                    @Override
                    public List<Comment> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        return CommentDao.select(grower, suffix, idPhoto, idUser, offset, limit);
                    }

                });

        return cms;

    }

}
