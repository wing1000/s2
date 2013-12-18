package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.Refresh;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RefreshTransducer implements Transducer<Refresh> {

    @Override
    public Refresh transform(ResultSet rs) throws SQLException {
        int idUser = rs.getInt("id_user");
        long idPhoto = rs.getLong("id_Photo");
        int updateAt = rs.getInt("at");
        String title = rs.getString("title");
        String userName = rs.getString("nice_name");
        byte category = rs.getByte("category");
        Refresh refresh = new Refresh(idPhoto, title, idUser, userName, updateAt);
        refresh.category = category;
        return refresh;
    }

}
