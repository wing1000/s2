package fengfei.ucm.dao;

import fengfei.fir.utils.AppUtils;
import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.LongTransducer;
import fengfei.ucm.dao.transducer.PhotoTagTransducer;
import fengfei.ucm.entity.photo.PhotoTag;

import java.sql.SQLException;
import java.util.List;

public class PhotoTagDao {

    final static String Insert = "INSERT INTO photo_tag%s(id_photo, name, category) VALUES (?,?,?)";
    final static String SelectExists = "SELECT id_photo FROM photo_tag%s WHERE id_photo = ?";
    final static String DeleteAllByPhotoId = "DELETE FROM photo_tag%s WHERE id_photo = ?";

    public static int[] saveTags(
            ForestGrower grower,
            String suffix,
            long idContent,
            byte category,
            String... tags) throws SQLException {
        List<Long> ids = grower.select(SelectExists, new LongTransducer(), idContent);
        if (ids != null && ids.size() > 0) {
            int deleted = grower.update(String.format(DeleteAllByPhotoId, suffix), idContent, category);
        }
        Object[][] params = new Object[tags.length][];
        for (int i = 0; i < tags.length; i++) {
            params[i] = new Object[]{idContent, tags[i], category};
        }
        int[] inserted = grower.batchUpdate(String.format(Insert, suffix), params);

        return inserted;
    }



    public static List<PhotoTag> fuzzilyFind(
            ForestGrower grower,
            String suffix,
            String name,
            Byte category,
            int offset,
            int limit) throws SQLException {
        return find(grower, suffix, name, category, true, offset, limit);
    }

    public static List<PhotoTag> preciselyFind(
            ForestGrower grower,
            String suffix,
            String name,
            Byte category,
            int offset,
            int limit) throws SQLException {
        return find(grower, suffix, name, category, false, offset, limit);
    }

    public static List<PhotoTag> find(
            ForestGrower grower,
            String suffix,
            String name,
            Byte category,
            boolean isFuzzed,
            int offset,
            int limit) throws SQLException {
        String sql = String.format("SELECT id_tag, id_photo, name, category FROM tags where name %s ? and category=? limit ?,?",
                isFuzzed ? " like " : " = ");
        List<PhotoTag> tags = grower.select(
                sql,
                new PhotoTagTransducer(),
                isFuzzed ? name : AppUtils.toLike(name),
                category,
                offset,
                limit);
        return tags;
    }

    public static List<PhotoTag> find(
            ForestGrower grower,
            String suffix,
            String name,
            boolean isFuzzed,
            int offset,
            int limit) throws SQLException {
        String select = "SELECT id_tag, id_photo, name, type FROM tags where name %s ? limit ?,? ";
        String sql = String.format(select, isFuzzed ? " like " : " = ");
        List<PhotoTag> tags = grower.select(
                sql,
                new PhotoTagTransducer(),
                isFuzzed ? name : AppUtils.toLike(name),
                offset,
                limit);
        return tags;
    }

    public static List<Long> findPhotoIds(
            ForestGrower grower,
            String suffix,
            String name,
            Byte category,
            boolean isFuzzed,
            int offset,
            int limit) throws SQLException {
        String select = "SELECT  id_photo, name FROM tags where name %s ? and category=? limit ?,?";
        String sql = String.format(select, isFuzzed ? " like " : " = ");
        List<Long> tags = grower.select(
                sql,
                new LongTransducer(),
                isFuzzed ? name : AppUtils.toLike(name),
                category,
                offset,
                limit);
        return tags;
    }

    public static List<Long> findPhotoIds(
            ForestGrower grower,
            String suffix,
            String name,
            boolean isFuzzed,
            int offset,
            int limit) throws SQLException {
        String select = "SELECT id_photo,name FROM tags where name %s ? limit ?,?";
        String sql = String.format(select, isFuzzed ? " like " : " = ");
        List<Long> tags = grower.select(
                sql,
                new LongTransducer(),
                isFuzzed ? name : AppUtils.toLike(name),
                offset,
                limit);
        return tags;
    }


}
