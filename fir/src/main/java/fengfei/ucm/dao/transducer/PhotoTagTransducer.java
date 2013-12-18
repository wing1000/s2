package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.PhotoTag;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotoTagTransducer implements Transducer<PhotoTag> {

    @Override
    public PhotoTag transform(ResultSet rs) throws SQLException {
        long idTag = rs.getLong("id_tag");
        long idPhoto = rs.getLong("id");
        byte category = rs.getByte("category");
        String name = rs.getString("name");
        PhotoTag tag = new PhotoTag(idTag, idPhoto, name, category);
        tag.idTag = idTag;
        return tag;
    }
}
