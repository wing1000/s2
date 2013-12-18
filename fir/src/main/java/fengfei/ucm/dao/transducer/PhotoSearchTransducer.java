package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.PhotoSearch;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotoSearchTransducer implements Transducer<PhotoSearch> {

    @Override
    public PhotoSearch transform(ResultSet rs) throws SQLException {
        long idSearch= rs.getLong("id_search");
        long idPhoto = rs.getLong("id_photo");
        long idUser = rs.getLong("id_user");
        PhotoSearch photoSearch=new PhotoSearch(idSearch,idPhoto,idUser);
        return photoSearch;
    }
}
