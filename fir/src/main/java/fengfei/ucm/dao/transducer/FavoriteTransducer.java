package fengfei.ucm.dao.transducer;

import fengfei.fir.utils.AppUtils;
import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.Favorite;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoriteTransducer implements Transducer<Favorite> {

    @Override
    public Favorite transform(ResultSet rs) throws SQLException {
        Integer idUser = rs.getInt("id_user");
        long idPhoto = rs.getLong("id_photo");
        long id = rs.getLong("id");
        int updateAt = rs.getInt("at");
        int cancel = rs.getInt("cancel");
       
        int iip=rs.getInt("ip");
        String title = rs.getString("title");
        String niceName = rs.getString("nice_name");
        byte category = rs.getByte("category");
        Favorite f = new Favorite(id, idPhoto, idUser, updateAt, cancel);
        f.title = title;
        f.niceName = niceName;
        f.category = category;
        f.iip=iip;
        f.ip=AppUtils.int2ip(iip);
        return f;
    }
}
