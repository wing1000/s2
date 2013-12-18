package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.YourPhoto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class YourPhotoTransducer implements Transducer<YourPhoto> {

    @Override
    public YourPhoto transform(ResultSet rs) throws SQLException {
        int idUser = rs.getInt("id_user");
        long idPhoto = rs.getInt("id_Photo");
        int updateAt = rs.getInt("at");
        String title = rs.getString("title");
        YourPhoto photo = new YourPhoto();
        photo.idPhoto = idPhoto;
        photo.idUser = idUser;
        photo.title = title;
        photo.updateAt = updateAt;
        return photo;
    }

}
