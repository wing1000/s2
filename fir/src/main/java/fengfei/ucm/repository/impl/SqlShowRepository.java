package fengfei.ucm.repository.impl;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.slice.SliceResource.Function;
import fengfei.forest.slice.database.utils.Transactions;
import fengfei.forest.slice.database.utils.Transactions.TaCallback;
import fengfei.ucm.dao.PhotoAccessDao;
import fengfei.ucm.dao.PhotoFavoriteDao;
import fengfei.ucm.dao.RankDao;
import fengfei.ucm.dao.ShowDao;
import fengfei.ucm.entity.photo.*;
import fengfei.ucm.entity.profile.UserPwd;
import fengfei.ucm.repository.ShowRepository;
import org.apache.commons.collections.MapUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlShowRepository implements ShowRepository {

    SqlUserRepository userRepository = new SqlUserRepository();

    @Override
    public List<Rank> selectUserPhotos(final Integer idUser, final int offset, final int limit)
            throws Exception {

        List<Rank> photos = Transactions.execute(
                UserUnitName,
                new Long(idUser),
                Function.Read,
                new TaCallback<List<Rank>>() {

                    @Override
                    public List<Rank> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<Rank> res = RankDao.selectUserTotalRank(
                                grower,
                                suffix,
                                idUser,
                                offset,
                                limit);

                        //
                        return res;
                    }

                });

        return photos;

    }

    @Override
    public List<Rank> pop(final int offset, final int limit) throws Exception {

        List<Rank> cms = Transactions.execute(
                UserUnitName,
                RefreshKey,
                Function.Read,
                new TaCallback<List<Rank>>() {

                    @Override
                    public List<Rank> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<Rank> res = RankDao.popRank(grower, suffix, offset, limit);

                        //
                        return res;
                    }

                });

        return cms;

    }

    private Map<Long, Map<String, Object>> toMap(List<Map<String, Object>> photos) {
        Map<Long, Map<String, Object>> maps = new HashMap<Long, Map<String, Object>>();
        for (Map<String, Object> map : photos) {
            Long id = MapUtils.getLong(maps, "id_photo");
            maps.put(id, map);
        }
        return maps;
    }

    @Override
    public List<Refresh> refreshed(final Byte category, final int offset, final int limit)
            throws Exception {

        List<Refresh> cms = Transactions.execute(
                UserUnitName,
                RefreshKey,
                Function.Read,
                new TaCallback<List<Refresh>>() {

                    @Override
                    public List<Refresh> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<Refresh> res = ShowDao.pagedRefreshPhoto(
                                grower,
                                suffix,
                                category,
                                offset,
                                limit);

                        return res;
                    }

                });

        return cms;

    }

    @Override
    public List<Rank> popular(final Byte category, final int offset, final int limit)
            throws Exception {

        List<Rank> cms = Transactions.execute(
                UserUnitName,
                RefreshKey,
                Function.Read,
                new TaCallback<List<Rank>>() {

                    @Override
                    public List<Rank> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<Rank> res = ShowDao.pagedPopularPhoto(
                                grower,
                                suffix,
                                category,
                                offset,
                                limit);

                        return res;
                    }

                });

        return cms;

    }

    @Override
    public List<Choice> choiced(final Byte category, final int offset, final int limit)
            throws Exception {

        List<Choice> cms = Transactions.execute(
                UserUnitName,
                RefreshKey,
                Function.Read,
                new TaCallback<List<Choice>>() {

                    @Override
                    public List<Choice> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<Choice> res = ShowDao.pagedChoicePhoto(
                                grower,
                                suffix,
                                category,
                                offset,
                                limit);

                        return res;
                    }

                });

        return cms;

    }

    @Override
    public List<Rank> upcomed(final Byte category, final int offset, final int limit)
            throws Exception {

        List<Rank> cms = Transactions.execute(
                UserUnitName,
                RefreshKey,
                Function.Read,
                new TaCallback<List<Rank>>() {

                    @Override
                    public List<Rank> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<Rank> res = ShowDao.pagedUpcomingPhoto(
                                grower,
                                suffix,
                                category,
                                offset,
                                limit);

                        return res;
                    }

                });

        return cms;

    }

    @Override
    public List<Refresh> categorized(final Byte category, final int offset, final int limit)
            throws Exception {

        List<Refresh> cms = Transactions.execute(
                UserUnitName,
                RefreshKey,
                Function.Read,
                new TaCallback<List<Refresh>>() {

                    @Override
                    public List<Refresh> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<Refresh> res = ShowDao.pagedCategoryPhoto(
                                grower,
                                suffix,
                                category,
                                offset,
                                limit);

                        return res;
                    }

                });

        return cms;

    }

    @Override
    public List<Rank> selectRanks(final List<Long> idPhotos) throws Exception {

        List<Rank> ranks = Transactions.execute(
                UserUnitName,
                RefreshKey,
                Function.Read,
                new TaCallback<List<Rank>>() {

                    @Override
                    public List<Rank> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<Rank> ranks = RankDao.selectTotalRank(grower, suffix, idPhotos);

                        return ranks;
                    }

                });

        return ranks;

    }

    @Override
    public Photo get(final long idPhoto, final Integer idUser) throws Exception {

        Photo photo = Transactions.execute(
                UserUnitName,
                RefreshKey,
                Function.Write,
                new TaCallback<Photo>() {

                    @Override
                    public Photo execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = "";
                        return ShowDao.getPhoto(grower, suffix, idPhoto, idUser);
                    }

                });

        return photo;
    }

    @Override
    public Rank getUserRank(final Integer idUser) throws Exception {

        Rank rank = Transactions.execute(
                UserUnitName,
                new Long(idUser),
                Function.Write,
                new TaCallback<Rank>() {

                    @Override
                    public Rank execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = "";
                        return RankDao.getUserTotalRank(grower, suffix, idUser);
                    }

                });

        return rank;
    }

    @Override
    public List<Favorite> selectFavorites(final Integer idUser, final int offset, final int limit)
            throws Exception {

        List<Favorite> cms = Transactions.execute(
                UserUnitName,
                new Long(idUser),
                Function.Read,
                new TaCallback<List<Favorite>>() {

                    @Override
                    public List<Favorite> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<Favorite> res = PhotoFavoriteDao.selectFavoritesByUser(
                                grower,
                                suffix,
                                idUser,
                                offset,
                                limit);

                        return res;
                    }

                });

        return cms;
    }

    @Override
    public List<Favorite> selectFavoritesByIP(final int iip, final int offset, final int limit)
            throws Exception {

        List<Favorite> cms = Transactions.execute(
                UserUnitName,
                new Long(iip),
                Function.Read,
                new TaCallback<List<Favorite>>() {

                    @Override
                    public List<Favorite> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<Favorite> res = PhotoFavoriteDao.selectFavoritesByIP(
                                grower,
                                suffix,
                                iip,
                                offset,
                                limit);

                        return res;
                    }

                });

        return cms;
    }

    @Override
    public List<Favorite> selectFavorites(String username, final int offset, final int limit)
            throws Exception {

        UserPwd up = userRepository.getUserPwd(username);
        return selectFavorites(up.idUser, offset, limit);
    }

    @Override
    public List<PhotoAccess> selectPhotoAccesses(
            final Integer idUser,
            final int offset,
            final int limit) throws Exception {

        List<PhotoAccess> cms = Transactions.execute(
                UserUnitName,
                new Long(idUser),
                Function.Read,
                new TaCallback<List<PhotoAccess>>() {

                    @Override
                    public List<PhotoAccess> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<PhotoAccess> res = PhotoAccessDao.selectPhotoAccessesByUser(
                                grower,
                                suffix,
                                idUser,
                                offset,
                                limit);

                        return res;
                    }

                });

        return cms;
    }

    @Override
    public List<PhotoAccess>
    selectPhotoAccesses(String username, final int offset, final int limit)
            throws Exception {
        UserPwd up = userRepository.getUserPwd(username);
        return selectPhotoAccesses(up.idUser, offset, limit);
    }

    @Override
    public List<PhotoAccess>
    selectPhotoViewsByIP(final int iip, final int offset, final int limit)
            throws Exception {

        List<PhotoAccess> cms = Transactions.execute(
                UserUnitName,
                new Long(iip),
                Function.Read,
                new TaCallback<List<PhotoAccess>>() {

                    @Override
                    public List<PhotoAccess> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<PhotoAccess> res = PhotoAccessDao.selectPhotoViewsByIP(
                                grower,
                                suffix,
                                iip,
                                offset,
                                limit);

                        return res;
                    }

                });

        return cms;
    }

    @Override
    public List<PhotoAccess> selectPhotoViewsByUserId(
            final Integer idUser,
            final int offset,
            final int limit) throws Exception {

        List<PhotoAccess> cms = Transactions.execute(
                UserUnitName,
                new Long(idUser),
                Function.Read,
                new TaCallback<List<PhotoAccess>>() {

                    @Override
                    public List<PhotoAccess> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        List<PhotoAccess> res = PhotoAccessDao.selectPhotoViewsByUser(
                                grower,
                                suffix,
                                idUser,
                                offset,
                                limit);

                        return res;
                    }

                });

        return cms;
    }
}
