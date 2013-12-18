package fengfei.ucm.dao.transducer;

import fengfei.fir.rank.RankUtils;
import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.Rank;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankTransducer implements Transducer<Rank> {

    @Override
    public Rank transform(ResultSet rs) throws SQLException {
        int idUser = rs.getInt("id_user");

        long idPhoto = rs.getInt("id_Photo");
        long updateAt = rs.getLong("update_at");
        int viewCount = rs.getInt("view");
        int voteCount = rs.getInt("vote");
        int favoriteCount = rs.getInt("favorite");
        int commentCount = rs.getInt("comment");
        float score = rs.getFloat("score");
        float maxScore = rs.getFloat("max_score");
        long maxAt = rs.getLong("max_at");
        String title = rs.getString("title");
        String username = rs.getString("username");
        Byte category = rs.getByte("category");
        Rank rank = new Rank(
            idPhoto,
            idUser,
            viewCount,
            voteCount,
            favoriteCount,
            commentCount,
            score,
            updateAt);
        rank.maxAt = maxAt;
        rank.maxScore = maxScore;
        rank.title = title;
        rank.niceName = username;
        rank.category = category;
        rank.sscore = RankUtils.decimalFormat.format(rank.score);
        return rank;
    }

}
