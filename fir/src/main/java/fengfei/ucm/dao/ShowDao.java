package fengfei.ucm.dao;

import fengfei.fir.utils.AppUtils;
import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.sprucy.AppConstants;
import fengfei.ucm.dao.transducer.ChoiceTransducer;
import fengfei.ucm.dao.transducer.PhotoTransducer;
import fengfei.ucm.dao.transducer.RankTransducer;
import fengfei.ucm.dao.transducer.RefreshTransducer;
import fengfei.ucm.entity.photo.Choice;
import fengfei.ucm.entity.photo.Photo;
import fengfei.ucm.entity.photo.Rank;
import fengfei.ucm.entity.photo.Refresh;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ShowDao {

    final static String SelectNoCategory = "SELECT id_photo, category, title, id_user,nice_name, at FROM photo_refresh%s  limit ?,?";
    final static String SelectCategory = "SELECT id_photo, category, title, id_user,nice_name, at FROM photo_refresh%s  where category=? limit ?,?";
    final static String SelectRefresh = "SELECT id_photo, category, title, id_user,nice_name, at FROM photo_refresh%s order by at desc limit ?,?";
    final static String SelectChoice = "SELECT id_photo, category, title, id_user,nice_name, at FROM photo_choice%s order by at desc limit ?,?";
    final static String SelectUpcoming = "SELECT  id_photo,title,id_user,username, category,view, vote, favorite, comment, score, update_at,max_score,max_at  FROM photo_rank%s where score>=? and score<? order by update_at desc limit ?,?";
    final static String SelectPopular = "SELECT  id_photo,title,id_user,username, category,view, vote, favorite, comment, score, update_at,max_score,max_at  FROM photo_rank%s  where score>=? order by score desc limit ?,?";
    final static String SelectRank = "SELECT  id_photo,title,id_user,username, category,view, vote, favorite, comment, score, update_at,max_score,max_at  FROM photo_rank%s";

    final static String SelectRefreshWithCategory = "SELECT id_photo, category, title, id_user,nice_name, at FROM photo_refresh%s where category=? order by at desc limit ?,?";
    final static String SelectChoiceWithCategory = "SELECT id_photo, category, title, id_user,nice_name, at FROM photo_choice%s  where category=? order by at desc limit ?,?";
    final static String SelectUpcomingWithCategory = "SELECT  id_photo,title,id_user,username, category,view, vote, favorite, comment, score, update_at,max_score,max_at  FROM photo_rank%s where score>=? and score<? and category=? order by update_at desc limit ?,?";
    final static String SelectPopularWithCategory = "SELECT  id_photo,title,id_user,username, category,view, vote, favorite, comment, score, update_at,max_score,max_at  FROM photo_rank%s  where score>=? and category=? order by score desc limit ?,?";
    final static String SelectRankWithCategory = "SELECT  id_photo,title,id_user,username, category,view, vote, favorite, comment, score, update_at,max_score,max_at  FROM photo_rank%s  where category=? limit ?,?";

    final static String GetPhoto = PhotoDao.SelectPhotoClause + " FROM photo%s where id_photo=?";
    final static String GetPhotoWithUser = PhotoDao.SelectPhotoClause + " FROM photo%s where id_photo=? and id_user=?";
    final static String SelectPhotos = PhotoDao.SelectPhotoClause + " FROM photo%s where id_photo in(%s)";

    public static List<Refresh> pagedRefreshPhoto(
            ForestGrower grower,
            String suffix,
            Byte category,
            int offset,
            int limit) throws SQLException {
        if (category == null) {
            List<Refresh> refreshes = grower.select(
                    String.format(SelectRefresh, suffix),
                    new RefreshTransducer(),
                    offset,
                    limit);
            return refreshes;
        } else {
            List<Refresh> refreshes = grower.select(
                    String.format(SelectRefreshWithCategory, suffix),
                    new RefreshTransducer(),
                    category,
                    offset,
                    limit);
            return refreshes;
        }

    }

    public static List<Choice> pagedChoicePhoto(
            ForestGrower grower,
            String suffix,
            Byte category,
            int offset,
            int limit) throws SQLException {
        if (category == null) {
            List<Choice> choices = grower.select(
                    String.format(SelectChoice, suffix),
                    new ChoiceTransducer(),
                    offset,
                    limit);
            return choices;
        } else {
            List<Choice> choices = grower.select(
                    String.format(SelectChoiceWithCategory, suffix),
                    new ChoiceTransducer(),
                    category,
                    offset,
                    limit);
            return choices;
        }
    }

    public static List<Rank> pagedUpcomingPhoto(
            ForestGrower grower,
            String suffix,
            Byte category,
            int offset,
            int limit) throws SQLException {
        if (category == null) {
            List<Rank> upcomings = grower.select(
                    String.format(SelectUpcoming, suffix),
                    new RankTransducer(),
                    AppConstants.UpcomingMinScore,
                    AppConstants.UpcomingMaxScore,
                    offset,
                    limit);
            return upcomings;
        } else {
            List<Rank> upcomings = grower.select(
                    String.format(SelectUpcomingWithCategory, suffix),
                    new RankTransducer(),
                    AppConstants.UpcomingMinScore,
                    AppConstants.UpcomingMaxScore,
                    category,
                    offset,
                    limit);
            return upcomings;
        }
    }

    public static List<Rank> pagedPopularPhoto(
            ForestGrower grower,
            String suffix,
            Byte category,
            int offset,
            int limit) throws SQLException {

        if (category == null) {
            List<Rank> upcomings = grower.select(
                    String.format(SelectPopular, suffix),
                    new RankTransducer(),
                    AppConstants.PopularMinScore,
                    offset,
                    limit);
            return upcomings;
        } else {
            List<Rank> upcomings = grower.select(
                    String.format(SelectPopularWithCategory, suffix),
                    new RankTransducer(),
                    AppConstants.PopularMinScore,
                    category,
                    offset,
                    limit);
            return upcomings;
        }
    }

    public static List<Refresh> pagedCategoryPhoto(
            ForestGrower grower,
            String suffix,
            Byte category,
            int offset,
            int limit) throws SQLException {
        if (category == null) {
            List<Refresh> categorized = grower.select(
                    String.format(SelectNoCategory, suffix),
                    new RefreshTransducer(),
                    offset,
                    limit);
            return categorized;
        } else {
            List<Refresh> categorized = grower.select(
                    String.format(SelectCategory, suffix),
                    new RefreshTransducer(),
                    category,
                    offset,
                    limit);
            return categorized;

        }
    }

    public static Photo getPhoto(
            ForestGrower grower,
            String suffix,
            long idPhoto,
            Integer photoIdUser) throws SQLException {

        Photo photo = grower.selectOne(
                String.format(GetPhotoWithUser, suffix),
                new PhotoTransducer(),
                idPhoto,
                photoIdUser);
        return photo;
    }

    public static List<Map<String, Object>> selectPhotos(
            ForestGrower grower,
            String suffix,
            List<Long> idPhotos) throws SQLException {
        String incause = AppUtils.toInCause(idPhotos);
        String sql = String.format(SelectPhotos, suffix, incause);
        List<Map<String, Object>> photos = grower.select(sql);
        return photos;
    }
}
