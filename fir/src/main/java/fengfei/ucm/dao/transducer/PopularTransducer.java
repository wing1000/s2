package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.Popular;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PopularTransducer implements Transducer<Popular> {

    @Override
    public Popular transform(ResultSet rs) throws SQLException {
        int idUser = rs.getInt("id_user");
        long idPhoto = rs.getLong("id_Photo");
        int updateAt = rs.getInt("at");
        String title = rs.getString("title");
        String userName = rs.getString("nice_name");
        byte category = rs.getByte("category");
        Popular popular = new Popular(idPhoto, title, idUser, userName, updateAt);
        popular.category = category;
        return popular;
    }

}
