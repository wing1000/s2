package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.Upcoming;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpcomingTransducer implements Transducer<Upcoming> {

    @Override
    public Upcoming transform(ResultSet rs) throws SQLException {
        int idUser = rs.getInt("id_user");
        long idPhoto = rs.getLong("id_Photo");
        int updateAt = rs.getInt("at");
        String title = rs.getString("title");
        String userName = rs.getString("nice_name");
        byte category = rs.getByte("category");
        Upcoming upcoming = new Upcoming(idPhoto, title, idUser, userName, updateAt);
        upcoming.category = category;
        return upcoming;
    }

}
