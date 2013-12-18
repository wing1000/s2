package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.Rank;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RanksTransducer implements Transducer<Rank> {

    @Override
    public Rank transform(ResultSet rs) throws SQLException {
        int idUser = rs.getInt("id_user");

        Date day = rs.getDate("day");

        long idPhoto = rs.getInt("id_Photo");
        long updateAt = rs.getLong("update_at");
        int viewCount = rs.getInt("view");
        int voteCount = rs.getInt("vote");
        int favoriteCount = rs.getInt("favorite");
        int commentCount = rs.getInt("comment");

        return new Rank(
            idPhoto,
            day,
            idUser,
            viewCount,
            voteCount,
            favoriteCount,
            commentCount,
            0,
            updateAt);
    }

}
