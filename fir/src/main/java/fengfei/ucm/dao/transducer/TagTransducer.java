package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.photo.Tag;
import fengfei.ucm.entity.photo.Tag.TagType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagTransducer implements Transducer<Tag> {

    @Override
    public Tag transform(ResultSet rs) throws SQLException {
        long idTag = rs.getLong("id_tag");
        long id = rs.getLong("id");
        int type = rs.getInt("type");
        String name = rs.getString("name");
        Tag tag = new Tag(id, TagType.valueOf(type), name);
        tag.idTag = idTag;
        return tag;

    }
}
