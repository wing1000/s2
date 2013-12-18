package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.ucm.dao.transducer.TagTransducer;
import fengfei.ucm.entity.photo.Tag;
import fengfei.ucm.entity.photo.Tag.TagType;

import java.sql.SQLException;
import java.util.List;

public class TagDao {

    final static String Insert = "INSERT INTO tags%s(id, name, type) VALUES (?,?,?)";
    final static String Delete = "DELETE FROM tags%s WHERE id = ? AND type = ?";

    public static int saveTags(
        ForestGrower grower,
        String suffix,
        long idContent,
        TagType type,
        String... tags) throws SQLException {
        int deleted = grower.update(String.format(Delete, suffix), idContent, type.getValue());
        Object[][] params = new Object[tags.length][];
        for (int i = 0; i < tags.length; i++) {
            params[i] = new Object[] { idContent, tags[i], type };
        }
        int[] inserted = grower.batchUpdate(String.format(Insert, suffix), params);

        return 1;
    }

    public static int saveTag(
        ForestGrower grower,
        String suffix,
        long idContent,
        TagType type,
        String tag) throws SQLException {
   
        int inserted = grower.update(String.format(Insert, suffix), idContent, tag, type );

        return 1;
    }

    public static List<Tag> find(
        ForestGrower grower,
        String suffix,
        String name,
        String type,
        int offset,
        int limit) throws SQLException {
        String sql = "SELECT id_tag, id, name, type FROM tags where name = ? and type=? limit ?,?";
        List<Tag> tags = grower.select(sql, new TagTransducer(), name, type, offset, limit);
        return tags;
    }

    public static List<Tag> find(
        ForestGrower grower,
        String suffix,
        String name,
        int offset,
        int limit) throws SQLException {
        String sql = "SELECT id_tag, id, name, type FROM tags where name = ? limit ?,? ";
        List<Tag> tags = grower.select(sql, new TagTransducer(), name, offset, limit);
        return tags;
    }
}
