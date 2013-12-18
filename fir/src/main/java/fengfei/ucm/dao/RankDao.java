package fengfei.ucm.dao;

import fengfei.fir.rank.RankUtils;
import fengfei.fir.utils.AppUtils;
import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.dao.transducer.RankTransducer;
import fengfei.ucm.dao.transducer.RanksTransducer;
import fengfei.ucm.dao.transducer.UserRankTransducer;
import fengfei.ucm.entity.photo.PhotoAccess.AccessType;
import fengfei.ucm.entity.photo.Rank;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RankDao {

    final static String InsertRanks = "INSERT INTO photo_ranks%s("
            + "id_photo,day,id_user, view, vote, favorite, comment, update_at)"
            + " VALUES (?,?, ?, ?, ?, ?, ?, ?)";

    final static String InsertRank = "INSERT INTO photo_rank%s("
            + "id_photo,title,id_user,username, view, vote, favorite, comment, score, update_at,max_score,max_at)"
            + " VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?,?,?)";

    final static String InsertUserStats = "INSERT INTO user_stats%s("
            + "id_user, view, vote, favorite, comment, affection, update_at)"
            + " VALUES (?, ?,?, ?, ?, ?, ?)";

    final static String UpdateViewTotal = "UPDATE photo_rank%s "
            + "SET score=?, update_at=?,view = view+?  where id_photo=? ";
    final static String UpdateVoteTotal = "UPDATE photo_rank%s "
            + "SET score=?, update_at=?, vote = vote+?  where id_photo=? ";
    final static String UpdateFavoriteTotal = "UPDATE photo_rank%s "
            + "SET  score=?, update_at=?,favorite = favorite+?  where id_photo=? ";
    final static String UpdateCommentTotal = "UPDATE photo_rank%s "
            + "SET  score=?, update_at=?,comment = comment+?  where id_photo=? ";
    final static String UpdateScore = "UPDATE photo_rank%s  SET score = ?, update_at = ? where id_photo=? ";
    final static String UpdateMaxScore = "UPDATE photo_rank%s SET max_score = ?, max_at = ? where id_photo=?  ";

    //
    final static String UpdateUserStatsViewTotal = "UPDATE user_stats%s "
            + "SET update_at=?,view = view+? where id_user=? ";
    final static String UpdateUserStatsVoteTotal = "UPDATE user_stats%s "
            + "SET update_at=?, vote = vote+?  where id_user=? ";
    final static String UpdateUserStatsAffectionTotal = "UPDATE user_stats%s "
            + "SET update_at=?, affection = affection+?  where id_user=? ";
    final static String UpdateUserStatsFavoriteTotal = "UPDATE user_stats%s "
            + "SET update_at=?,favorite = favorite+?  where id_user=? ";
    final static String UpdateUserStatsCommentTotal = "UPDATE user_stats%s "
            + "SET update_at=?,comment = comment+?  where id_user=? ";
    //

    final static String UpdateViewCount = "UPDATE photo_ranks%s "
            + " SET view = view+?, update_at = ? where id_photo=?  and day=?";
    final static String UpdateVoteCount = "UPDATE photo_ranks%s "
            + " SET vote = vote+?, update_at = ? where id_photo=?  and day=? ";
    final static String UpdateFavoriteCount = "UPDATE photo_ranks%s "
            + " SET favorite = favorite+?, update_at = ? where id_photo=?   and day=? ";
    final static String UpdateCommentCount = "UPDATE photo_ranks%s "
            + " SET comment = comment+?, update_at = ? where id_photo=?   and day=? ";
    //
    final static String SelectLast30DaysRanks = "SELECT sum(view), sum(vote), sum(favorite), sum(comment) "
            + " FROM photo_ranks%s where id_photo=? and day>=DATE_SUB(CURDATE(), INTERVAL 30 DAY)";
    //

    final static String SelectRanks = "SELECT id_photo,day,id_user, view, vote, favorite, comment,  update_at,max_score,max_at  "
            + " FROM photo_ranks%s where id_photo=? and day=?";
    //
    final static String SelectAllRank = "SELECT id_photo,title,id_user,username,category,view, vote, favorite, comment, score, update_at,max_score,max_at "
            + " FROM photo_rank%s where id_photo=?";

    final static String SelectUserRank = "SELECT id_user,view, vote, favorite, comment, affection, update_at "
            + " FROM user_stats%s where id_user=?";

    final static String SelectAllRanks = "SELECT id_photo,title,id_user,username,category,view, vote, favorite, comment, score, update_at,max_score,max_at  "
            + " FROM photo_rank%s where id_photo in(%s)";
    final static String SelectAllRankForLock = "SELECT id_photo,title,id_user,username,category,  view, vote, favorite, comment, score, update_at,max_score,max_at "
            + " FROM photo_rank%s where id_photo=? for update";
    final static String SelectRankPop = "SELECT id_photo,title,id_user,username,category, view, vote, favorite, comment, score, update_at,max_score,max_at "
            + " FROM photo_rank%s  order by score desc limit ?,?";

    //
    final static String SelectUserAllRanks = "SELECT id_photo,title,id_user,username,category,view, vote, favorite, comment, score, update_at,max_score,max_at  "
            + " FROM photo_rank%s where id_user=? limit ?,?";

    final static String CountUserRank = "SELECT sum(`view`),sum(vote),sum(favorite),sum(`comment`) FROM photo_rank%s WHERE id_user = ?";
    // Search
    final static String SearchRank = "SELECT id_photo,title,id_user,username, category,view, vote, favorite, comment, score, update_at,max_score,max_at "
            + " FROM photo_rank%s where title like ? order by score desc limit ?,?";
    final static String SearchRankByCategory = "SELECT id_photo,title,id_user,username,category, view, vote, favorite, comment, score, update_at,max_score,max_at "
            + " FROM photo_rank%s where category=? and title like ? order by score desc limit ?,?";

    public static int addRanks(ForestGrower grower, String suffix, Rank m) throws SQLException {
        String insert = String.format(InsertRanks, suffix);

        m.day = new Date(System.currentTimeMillis());
        int updated = grower.update(
                insert,
                m.idPhoto,
                m.day,
                m.idUser,
                m.view,
                m.vote,
                m.favorite,
                m.comment,
                m.updateAt);
        return updated;

    }

    public static int
    addUserRank(ForestGrower grower, String suffix, Integer idUser, long updateAt)
            throws SQLException {
        String insert = String.format(InsertUserStats, suffix);
        int updated = grower.update(insert, idUser, 0, 0, 0, 0, 0, updateAt);
        return updated;

    }

    public static int addRank(ForestGrower grower, String suffix, Rank m) throws SQLException {
        String insert = String.format(InsertRank, suffix);
        int updated = grower.update(
                insert,
                m.idPhoto,
                m.title,
                m.idUser,
                m.niceName,
                m.view,
                m.vote,
                m.favorite,
                m.comment,
                m.score,
                m.updateAt,
                m.score,
                m.updateAt);

        int updated2 = addRanks(grower, suffix, m);
        return updated & updated2;

    }

    public static Rank getRank(ForestGrower grower, String suffix, long idPhoto)
            throws SQLException {

        String sql = String.format(SelectAllRank, suffix);
        Rank rank = grower.selectOne(sql, new RankTransducer(), idPhoto);
        return rank;

    }

    public static Rank getRankForLock(ForestGrower grower, String suffix, long idPhoto)
            throws SQLException {

        String sql = String.format(SelectAllRankForLock, suffix);
        Rank rank = grower.selectOne(sql, new RankTransducer(), idPhoto);
        return rank;

    }

    public static List<Rank> selectTotalRank(
            ForestGrower grower,
            String suffix,
            Collection<Long> idPhotos) throws SQLException {
        String incause = AppUtils.toInCause(idPhotos);
        if ("".equals(incause.trim())) {
            return new ArrayList<Rank>();
        } else {
            String sql = String.format(SelectAllRanks, suffix, incause);
            List<Rank> ranks = grower.select(sql, new RankTransducer());
            return ranks;
        }

    }

    public static List<Rank> selectUserTotalRank(
            ForestGrower grower,
            String suffix,
            Integer idUser,
            int offset,
            int limit) throws SQLException {
        String sql = String.format(SelectUserAllRanks, suffix);
        List<Rank> ranks = grower.select(sql, new RankTransducer(), idUser, offset, limit);
        return ranks;

    }

    public static List<Rank> searchRank(
            ForestGrower grower,
            String suffix,
            String qstr,
            int offset,
            int limit) throws SQLException {
        String sql = String.format(SearchRank, suffix);
        List<Rank> ranks = grower.select(
                sql,
                new RankTransducer(),
                AppUtils.toLike(qstr),
                offset,
                limit);
        return ranks;

    }

    public static List<Rank> searchRank(
            ForestGrower grower,
            String suffix,
            String qstr,
            Byte category,
            int offset,
            int limit) throws SQLException {
        String sql = String.format(SearchRankByCategory, suffix);
        List<Rank> ranks = grower.select(
                sql,
                new RankTransducer(),
                category,
                AppUtils.toLike(qstr),

                offset,
                limit);
        return ranks;

    }

    public static Rank getUserTotalRank(ForestGrower grower, String suffix, Integer idUser)
            throws SQLException {
        String sql = String.format(SelectUserRank, suffix);
        Rank rank = grower.selectOne(sql, new UserRankTransducer(), idUser);
        return rank;

    }

    public static List<Rank> getDayRank(
            ForestGrower grower,
            String suffix,
            long idPhoto,
            String day) throws SQLException {

        String sql = String.format(SelectRanks, suffix);
        List<Rank> ranks = grower.select(sql, new RanksTransducer(), idPhoto, day);
        return ranks;

    }

    public static List<Rank> popRank(ForestGrower grower, String suffix, int offset, int limit)
            throws SQLException {

        String sql = String.format(SelectRankPop, suffix);
        List<Rank> ranks = grower.select(sql, new RankTransducer(), offset, limit);
        return ranks;

    }

    public static int[] last30DaysRank(ForestGrower grower, String suffix, long idPhoto)
            throws SQLException {

        String sql = String.format(SelectLast30DaysRanks, suffix);
        int[] rank = grower.selectOne(sql, new Transducer<int[]>() {

            @Override
            public int[] transform(ResultSet rs) throws SQLException {
                int[] ct = new int[4];
                ct[0] = rs.getInt(1);
                ct[1] = rs.getInt(2);
                ct[2] = rs.getInt(3);
                ct[3] = rs.getInt(4);
                return ct;
            }

        }, idPhoto);
        return rank;

    }

    public static int updateMaxScore(
            ForestGrower grower,
            String suffix,
            Rank rank,
            long idPhoto,
            double score) throws SQLException {
        if (score > rank.maxScore) {
            String sql = String.format(UpdateMaxScore, suffix);
            rank.maxAt = rank.updateAt / 1000;
            rank.maxScore = score;
            grower.update(sql, score, rank.maxAt, idPhoto);

        }
        return 1;
    }

    public static Rank updateRank(
            ForestGrower grower,
            String suffix,
            long idPhoto,
            Integer idUser,
            AccessType type,
            int value) throws SQLException {
        String updateSQL = null;
        String upTotal = null;
        String userTotal = null;
        String userAffectionTotal = null;
        // 获取rank并锁定记录
        Rank rank = getRankForLock(grower, suffix, idPhoto);
        switch (type) {
            case View:
                updateSQL = UpdateViewCount;
                upTotal = UpdateViewTotal;
                userTotal = UpdateUserStatsViewTotal;
                rank.view += value;
                break;
            case Vote:
                updateSQL = UpdateVoteCount;
                upTotal = UpdateVoteTotal;
                userTotal = UpdateUserStatsVoteTotal;
                userAffectionTotal = UpdateUserStatsAffectionTotal;
                rank.vote += value;
                break;
            case Favorite:
                updateSQL = UpdateFavoriteCount;
                upTotal = UpdateFavoriteTotal;
                userTotal = UpdateUserStatsFavoriteTotal;
                rank.favorite += value;
                break;
            case Comment:
                updateSQL = UpdateCommentCount;
                upTotal = UpdateCommentTotal;
                userTotal = UpdateUserStatsCommentTotal;
                rank.comment += value;
                break;
            // case Score:
            // updateSQL = UpdateScore;
            // break;

            default:
                return rank;
            // break;
        }
        long current = System.currentTimeMillis();

        int updated = 0;

        // 更新天访问数/投票数/收藏数/评论数
        Date day = new Date(System.currentTimeMillis());
        String update = String.format(updateSQL, suffix);
        int updated2 = grower.update(update, value, current, idPhoto, day);
        rank.updateAt = current;
        // 计算分数
        int[] lastRank = last30DaysRank(grower, suffix, idPhoto);
        int likesIn30Days = lastRank[1];
        double score = RankUtils.score(
                rank.view,
                rank.vote,
                likesIn30Days,
                rank.favorite,
                rank.comment);

        // 更新总访问数/总投票数/总收藏数/总评论数
        if (type != AccessType.Score) {
            String update1 = String.format(upTotal, suffix);
            updated = grower.update(update1, score, current, value, idPhoto);
            rank.score = score;
            if (idUser != null && idUser > 0) {
                String update2 = String.format(userTotal, suffix);
                updated = grower.update(update2, current, value, idUser);
                if (userAffectionTotal != null) {
                    String update3 = String.format(userAffectionTotal, suffix);
                    updated = grower.update(update3, current, value, idUser);
                }
            }
        }

        updateMaxScore(grower, suffix, rank, idPhoto, score);
        return rank;

    }

}
