package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.PhotoSet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotoSetTransducer implements Transducer<PhotoSet> {

    @Override
    public PhotoSet transform(ResultSet rs) throws SQLException {
        long idSet = rs.getLong("id_set");
        int idUser = rs.getInt("id_user");
        String name = rs.getString("name");
        int createAt = rs.getInt("create_at");
        long updateAt = rs.getLong("update_at");
        PhotoSet set = new PhotoSet();
        set.idSet = idSet;
        set.name = name;
        set.idUser = idUser;
        set.createAt = createAt;
        set.updateAt = updateAt;
        return set;

    }
}
