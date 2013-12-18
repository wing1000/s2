package fengfei.ucm.repository.impl;

import fengfei.fir.queue.PhotoQueue;
import fengfei.fir.rank.LastRank;
import fengfei.fir.rank.RankUtils;
import fengfei.fir.utils.AppUtils;
import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.impl.ForestRunner.InsertResultSet;
import fengfei.forest.slice.SliceResource.Function;
import fengfei.forest.slice.database.PoolableDatabaseResource;
import fengfei.forest.slice.database.utils.Transactions;
import fengfei.forest.slice.database.utils.Transactions.TaCallback;
import fengfei.forest.slice.database.utils.Transactions.TransactionCallback;
import fengfei.sprucy.AppConstants;
import fengfei.ucm.dao.*;
import fengfei.ucm.entity.photo.Favorite;
import fengfei.ucm.entity.photo.Photo;
import fengfei.ucm.entity.photo.PhotoAccess;
import fengfei.ucm.entity.photo.PhotoAccess.AccessType;
import fengfei.ucm.entity.photo.Rank;
import fengfei.ucm.entity.profile.User;
import fengfei.ucm.entity.profile.UserPwd;
import fengfei.ucm.repository.PhotoExtRepository;
import fengfei.ucm.repository.PhotoRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SqlPhotoRepository implements PhotoRepository {

    PhotoExtRepository photoExtRepository = new RedisPhotoExtRepository();
    public final static String PhotosTableName = "photo";
    LastRank last = new LastRank();

    @Override
    public List<InsertResultSet<Long>> save(List<Photo> models, final String userName)
            throws Exception {
        List<InsertResultSet<Long>> insertResultSets = new ArrayList<>();
        for (Photo exifModel : models) {
            InsertResultSet<Long> u = saveOne(exifModel, userName);
            insertResultSets.add(u);
        }

        return insertResultSets;
    }

    @Override
    public InsertResultSet<Long> saveOne(final Photo m, final String userName)
            throws Exception {
        if (m.idPhoto <= 0) {
            m.idPhoto = Sequence.next(PhotosTableName);
        }
        TransactionCallback<InsertResultSet<Long>> callback = new TransactionCallback<InsertResultSet<Long>>() {

            @Override
            public InsertResultSet<Long> execute(
                    ForestGrower grower,
                    PoolableDatabaseResource resource) throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";
                InsertResultSet<Long> u = PhotoDao.saveOne(grower, suffix, m, userName);
                last.add(m.idPhoto, m.createdAt);

                PhotoSetDao.addPhotoSets(grower, suffix, m.idUser, m.idSet, m.idPhoto);

                return u;
            }
        };

        InsertResultSet<Long> updated = Transactions.execute(PhotoUnitName, new Long(m.idPhoto), Function.Write, callback);
        if (updated.autoPk != null && updated.autoPk > 0) {
            PhotoQueue.add(m);
        }
        return updated;

    }

    @Override
    public boolean deleteOne(final long idPhoto, final Integer idUser) throws Exception {

        TransactionCallback<Boolean> callback = new TransactionCallback<Boolean>() {

            @Override
            public Boolean execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";
                boolean up1 = PhotoDeleteDao.deleteOne(grower, suffix, idPhoto, idUser);
                // last.
                return up1;
            }
        };

        boolean deleted = Transactions.execute(PhotoUnitName, new Long(idUser), Function.Write, callback);
        if (deleted) {
            Photo photo = new Photo();
            photo.idPhoto = idPhoto;
            photo.idUser = idUser;
            PhotoQueue.delete(photo);
        }
        return deleted;

    }

    // @Override
    // public InsertResultSet<Long> addOne(final Photo m, final String userName)
    // throws Exception {
    // if (m.idPhoto <= 0) {
    // m.idPhoto = Sequence.next(PhotosTableName);
    // }
    //
    // TransactionCallback<InsertResultSet<Long>> callback = new
    // TransactionCallback<InsertResultSet<Long>>() {
    //
    // @Override
    // public InsertResultSet<Long> execute(
    // ForestGrower grower,
    // PoolableDatabaseResource resource) throws SQLException {
    // String suffix = resource.getAlias();
    // suffix = "";
    // InsertResultSet<Long> u = PhotoDao.addOne(grower, suffix, m, userName);
    // last.add(m.idPhoto, m.createdAt);
    // return u;
    // }
    // };
    // return Transactions.execute(PhotoUnitName, new Long(m.idPhoto), Function.Write, callback);
    //
    // }

    @Override
    public int updateOne(final Photo m, final String userName) throws Exception {
        if (m.idPhoto <= 0) {
            m.idPhoto = Sequence.next(PhotosTableName);
        }

        TransactionCallback<Integer> callback = new TransactionCallback<Integer>() {

            @Override
            public Integer execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";
                Integer u = PhotoDao.updateOne(grower, suffix, m, userName);
                last.add(m.idPhoto, m.createdAt);

                PhotoSetDao.updatePhotoSets(grower, suffix, m.idSet, m.idUser, m.idPhoto);

                return u;
            }
        };
        int updated = Transactions.execute(PhotoUnitName, new Long(m.idPhoto), Function.Write, callback);
        if (updated > 0) {
            PhotoQueue.update(m);
        }
        return updated;
    }

    @Override
    public Photo selectOne(final long idPhoto, final Integer idUser) throws Exception {

        TransactionCallback<Photo> callback = new TransactionCallback<Photo>() {

            @Override
            public Photo execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";
                return PhotoDao.selectOne(grower, suffix, idPhoto, idUser);
            }
        };
        return Transactions.execute(UserUnitName, new Long(idUser), Function.Read, callback);
    }

    @Override
    public Photo selectOne(final long idPhoto) throws Exception {

        TransactionCallback<Photo> callback = new TransactionCallback<Photo>() {

            @Override
            public Photo execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";
                return PhotoDao.selectOne(grower, suffix, idPhoto);
            }
        };
        return Transactions.execute(UserUnitName, new Long(idPhoto), Function.Read, callback);
    }

    public Rank getRank(final long idPhoto) throws Exception {
        TransactionCallback<Rank> callback = new TransactionCallback<Rank>() {

            @Override
            public Rank execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";
                Rank rank = RankDao.getRank(grower, suffix, idPhoto);
                rank.sMaxScore = RankUtils.decimalFormat.format(rank.maxScore);
                rank.sMaxAt = AppUtils.dateFormat.format(new Date(rank.maxAt * 1000));
                return rank;
            }
        };
        return Transactions.execute(UserUnitName, new Long(idPhoto), Function.Read, callback);
    }

    @Override
    public Photo view(
            final long idPhoto,
            final Integer photoIdUser,
            final Integer accessIdUser,
            final int iip) throws Exception {
        // TODO 要根据访问者ID或者IP分片，如果是登录用户ID分片，否则IP分片（可以直接使用int型IP），
        // 所以这里的photo查询和写要在不同的事务里，那么就需要把photo所有者的User Id也要传进来

        long current = System.currentTimeMillis();
        final int updateAt = (int) (current / 1000);

        Photo photo = Transactions.execute(
                UserUnitName,
                new Long(photoIdUser),
                Function.Write,
                new TaCallback<Photo>() {

                    @Override
                    public Photo execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = "";
                        Photo photo = ShowDao.getPhoto(grower, suffix, idPhoto, photoIdUser);
                        if (photo == null) {
                            return null;
                        }
                        //
                        UserPwd up = UserDao.getUserPwd(grower, "", photo.idUser);
                        if (up != null) {
                            User user = UserDao.getUser(grower, suffix, photo.idUser);
                            if (user == null) {
                                user = new User();
                            }
                            user.setUserPwd(up);
                            photo.user = user;
                            user.niceName = AppUtils.toNiceName(user);
                        } else {
                            photo.user = new User();
                        }

                        return photo;
                    }

                });
        final Photo thatPhoto = photo;
        photo = Transactions.execute(UserUnitName, new Long(accessIdUser == null ? iip
                : accessIdUser), Function.Write, new TaCallback<Photo>() {

            @Override
            public Photo execute(ForestGrower grower, String suffix) throws SQLException {
                suffix = "";
                Photo photo = thatPhoto;
                PhotoAccess pa = new PhotoAccess(
                        idPhoto,
                        photo.title,
                        photo.idUser,
                        photo.user.niceName,
                        updateAt,
                        0,
                        AccessType.View);

                pa.iip = iip;

                pa.category = photo.category;
                pa.accessIdUser = accessIdUser;
                int updated = PhotoAccessDao.addPhotoAccess(grower, suffix, pa);
                Rank rank = new Rank();
                if (updated > 0) {
                    rank = RankDao.updateRank(
                            grower,
                            suffix,
                            idPhoto,
                            photo.idUser,
                            AccessType.View,
                            1);
                } else {
                    rank = RankDao.getRank(grower, suffix, idPhoto);
                }
                rank.sMaxScore = RankUtils.decimalFormat.format(rank.maxScore);
                rank.sMaxAt = AppUtils.dateFormat.format(new Date(rank.maxAt * 1000));
                //
                // Rank rank = PhotoAccessDao.getRank(grower,
                // suffix, idPhoto);
                //

                photo.rank = rank;
                //

                return photo;
            }

        });

        photo.tagList = photo.tags == null || "".equals(photo.tags)
                ? null
                : photo.tags.split(AppConstants.TextSplitRegex);

        if (photo.rank != null) {
            photoExtRepository.updateUpcoming(photo.rank);
            photoExtRepository.updatePopular(photo.rank);
            photoExtRepository.deletePopular(photo.rank);

        }

        return photo;

    }

    @Override
    public int vote(
            final long idPhoto,
            final Integer photoIdUser,
            final String photoNiceName,
            final byte photoCategory,
            final Integer accessIdUser,
            final int iip) throws Exception {
        // TODO 要根据访问者ID或者IP分片，如果是登录用户ID分片，否则IP分片（可以直接使用int型IP），
        // 所以这里的photo查询和写要在不同的事务里，那么就需要把photo所有者的User Id也要传进来

        Photo photo = Transactions.execute(
                UserUnitName,
                new Long(photoIdUser),
                Function.Write,
                new TaCallback<Photo>() {

                    @Override
                    public Photo execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = "";
                        Photo photo = PhotoDao.selectOne(grower, suffix, idPhoto);

                        return photo;
                    }

                });
        final Photo thatPhoto = photo;
        RankModel rm = Transactions.execute(PhotoUnitName, new Long(accessIdUser == null ? iip
                : accessIdUser), Function.Write, new TaCallback<RankModel>() {

            @Override
            public RankModel execute(ForestGrower grower, String suffix) throws SQLException {
                suffix = "";
                long curr = System.currentTimeMillis();
                int updateAt = (int) (curr / 1000);
                Photo photo = thatPhoto;
                String title = photo.title;
                PhotoAccess access = new PhotoAccess(
                        idPhoto,
                        title,
                        photo.idUser,
                        photoNiceName,
                        updateAt,
                        0,

                        AccessType.Vote);
                access.iip = iip;
                access.accessIdUser = accessIdUser;
                access.category = photo.category;
                int updated = PhotoAccessDao.addPhotoAccess(grower, suffix, access);
                RankModel rm = new RankModel();
                if (updated > 0) {
                    Rank rank = RankDao.updateRank(
                            grower,
                            suffix,
                            idPhoto,
                            photo.idUser,
                            AccessType.Vote,
                            1);
                    rm.rank = rank;
                    rm.updated = 1;
                    return rm;

                }
                rm.updated = 0;
                return rm;
            }

        });
        if (rm.rank != null) {
            photoExtRepository.updateUpcoming(rm.rank);
            photoExtRepository.updatePopular(rm.rank);
            photoExtRepository.deletePopular(rm.rank);
        }
        return rm.updated;

    }

    @Override
    public int favorite(
            final long idPhoto,
            final Integer photoIdUser,
            final String photoNiceName,
            final byte photoCategory,
            final Integer accessIdUser,
            final int iip) throws Exception {
        // TODO 要根据访问者ID或者IP分片，如果是登录用户ID分片，否则IP分片（可以直接使用int型IP），
        // 所以这里的photo查询和写要在不同的事务里，那么就需要把photo所有者的User Id也要传进来

        long current = System.currentTimeMillis();
        final int updateAt = (int) (current / 1000);
        Photo photo = Transactions.execute(
                UserUnitName,
                new Long(photoIdUser),
                Function.Write,
                new TaCallback<Photo>() {

                    @Override
                    public Photo execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = "";
                        Photo photo = PhotoDao.selectOne(grower, suffix, idPhoto);

                        return photo;
                    }

                });
        final Photo thatPhoto = photo;
        RankModel rm = Transactions.execute(UserUnitName, new Long(accessIdUser == null ? iip
                : accessIdUser), Function.Write, new TaCallback<RankModel>() {

            @Override
            public RankModel execute(ForestGrower grower, String suffix) throws SQLException {
                suffix = "";
                Photo photo = thatPhoto;//
                Favorite favorite = new Favorite(
                        idPhoto,
                        photo.title,
                        photo.idUser,
                        photoNiceName,
                        updateAt,
                        0);
                favorite.iip = iip;
                favorite.accessIdUser = accessIdUser;
                favorite.category = photo.category;
                int[] updated = PhotoFavoriteDao.addPhotoFavorite(grower, suffix, favorite);
                RankModel rm = new RankModel();
                if (updated[0] == 1 && updated[1] > 0) {
                    Rank rank = RankDao.updateRank(
                            grower,
                            suffix,
                            idPhoto,
                            photo.idUser,
                            AccessType.Favorite,
                            1);
                    rm.rank = rank;

                }
                rm.updated = updated[1];
                return rm;
            }

        });
        if (rm.rank != null) {
            photoExtRepository.updateUpcoming(rm.rank);
            photoExtRepository.updatePopular(rm.rank);
            photoExtRepository.deletePopular(rm.rank);
        }
        return rm.updated;

    }

    @Override
    public int unfavorite(
            final long idPhoto,
            final Integer photoIdUser,
            final Integer accessIdUser,
            final int iip) throws Exception {
        // TODO 要根据访问者ID或者IP分片，如果是登录用户ID分片，否则IP分片（可以直接使用int型IP），
        // 所以这里的photo查询和写要在不同的事务里，那么就需要把photo所有者的User Id也要传进来
        RankModel rm = Transactions.execute(UserUnitName, new Long(accessIdUser == null ? iip
                : accessIdUser), Function.Write, new TaCallback<RankModel>() {

            @Override
            public RankModel execute(ForestGrower grower, String suffix) throws SQLException {
                suffix = "";
                int updated = PhotoFavoriteDao.cancelPhotoFavorite(
                        grower,
                        suffix,
                        idPhoto,
                        accessIdUser,
                        iip);
                RankModel rm = new RankModel();
                if (updated > 0) {
                    Rank rank = RankDao.updateRank(
                            grower,
                            suffix,
                            idPhoto,
                            photoIdUser,
                            AccessType.Favorite,
                            -1);
                    rm.rank = rank;
                    rm.updated = 1;
                    return rm;
                }
                rm.updated = 1;
                return rm;
            }

        });
        if (rm.rank != null) {
            photoExtRepository.updateUpcoming(rm.rank);
            photoExtRepository.updatePopular(rm.rank);
            photoExtRepository.deletePopular(rm.rank);
        }
        return rm.updated;

    }

    @Override
    public boolean isFavorite(final long idPhoto, final Integer accessIdUser, final int iip)
            throws Exception {
        // TODO 要根据访问者ID或者IP分片，如果是登录用户ID分片，否则IP分片（可以直接使用int型IP），
        TransactionCallback<Boolean> callback = new TransactionCallback<Boolean>() {

            @Override
            public Boolean execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";
                return PhotoFavoriteDao.isFavorite(grower, suffix, accessIdUser, idPhoto, iip);
            }
        };
        return Transactions.execute(UserUnitName, new Long(accessIdUser == null ? iip
                : accessIdUser), Function.Read, callback);
    }

    @Override
    public boolean isVote(final long idPhoto, final Integer accessIdUser, final int iip)
            throws Exception {
        // TODO 要根据访问者ID或者IP分片，如果是登录用户ID分片，否则IP分片（可以直接使用int型IP），
        TransactionCallback<Boolean> callback = new TransactionCallback<Boolean>() {

            @Override
            public Boolean execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";

                return PhotoAccessDao.isAccessed(
                        grower,
                        suffix,
                        accessIdUser,
                        idPhoto,
                        iip,
                        AccessType.Vote);
            }
        };
        return Transactions.execute(UserUnitName, new Long(accessIdUser == null ? iip
                : accessIdUser), Function.Read, callback);
    }

    public static class RankModel {

        public int updated;
        public Rank rank;
    }
}
