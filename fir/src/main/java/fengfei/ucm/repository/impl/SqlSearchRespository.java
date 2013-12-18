package fengfei.ucm.repository.impl;

import fengfei.fir.utils.Hash;
import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.slice.SliceResource.Function;
import fengfei.forest.slice.database.utils.Transactions;
import fengfei.forest.slice.database.utils.Transactions.TaCallback;
import fengfei.ucm.dao.PhotoTagDao;
import fengfei.ucm.dao.RankDao;
import fengfei.ucm.dao.UserDao;
import fengfei.ucm.entity.photo.Rank;
import fengfei.ucm.entity.profile.User;
import fengfei.ucm.repository.SearchRespository;

import java.sql.SQLException;
import java.util.List;

public class SqlSearchRespository implements SearchRespository {

    SqlShowRepository showRepository = new SqlShowRepository();

    @Override
    public List<Rank> selectPhotosByTag(
            final String qstr,
            final Byte category,
            final int offset,
            final int limit) throws Exception {
        long seed = Hash.murmurHash(qstr);
        List<Long> idPhotos = Transactions.execute(
                PhotoUnitName,
                seed,
                Function.Write,
                new TaCallback<List<Long>>() {

                    @Override
                    public List<Long> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        if (category == null) {
                            return PhotoTagDao.findPhotoIds(grower, suffix, qstr, true, offset, limit);
                        } else {
                            return PhotoTagDao.findPhotoIds(
                                    grower,
                                    suffix,
                                    qstr,
                                    category, true,
                                    offset,
                                    limit);
                        }
                    }

                });
        List<Rank> ranks = showRepository.selectRanks(idPhotos);
        return ranks;
    }

    @Override
    public List<Rank> selectPhotos(
            final String qstr,
            final Byte category,
            final int offset,
            final int limit) throws Exception {
        long seed = Hash.murmurHash(qstr);
        List<Rank> ranks = Transactions.execute(
                PhotoUnitName,
                seed,
                Function.Write,
                new TaCallback<List<Rank>>() {

                    @Override
                    public List<Rank> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        if (category == null) {
                            return RankDao.searchRank(grower, suffix, qstr, offset, limit);
                        } else {
                            return RankDao.searchRank(
                                    grower,
                                    suffix,
                                    qstr,
                                    category,
                                    offset,
                                    limit);
                        }
                    }

                });
        return ranks;
    }

    @Override
    public List<User> selectUsers(final String qstr, final int offset, final int limit)
            throws Exception {

        List<User> users = Transactions.execute(
                UserUnitName,
                UserPwdSliceId,
                Function.Read,
                new TaCallback<List<User>>() {

                    @Override
                    public List<User> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<Integer> idUsers = UserDao.searchUserIdsByUserName(
                                grower,
                                suffix,
                                qstr,
                                offset,
                                limit);
                        return UserDao.selectUserList(grower, suffix, idUsers);
                    }

                });

        return users;
    }

}
