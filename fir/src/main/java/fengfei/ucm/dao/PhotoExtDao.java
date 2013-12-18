package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.ucm.entity.photo.Choice;
import fengfei.ucm.entity.photo.Refresh;

import java.sql.SQLException;
import java.util.Map;

public class PhotoExtDao {

    final static String RefreshInsert = "INSERT INTO photo_refresh%s(id_photo,category,title, id_user,nice_name, at) VALUES (?,?, ?, ?, ?, ?)";
    final static String RefreshDelete = "delete from photo_refresh%s where id_photo=?";
    final static String RefreshDeleteBeforeAt = "delete from photo_refresh%s where at<=?";
    //
    final static String ChoiceInsert = "INSERT INTO photo_choice%s(id_photo,category,title, id_user,nice_name, at) VALUES (?,?, ?, ?, ?, ?)";
    final static String ChoiceDelete = "delete from photo_choice%s where id_photo=?";
    final static String ChoiceDeleteBeforeAt = "delete from photo_choice%s where at<=?";
    final static String ChoiceExists = "SELECT id_user from photo_choice%s where id_photo=?";

    //

    public static int addRefresh(ForestGrower grower, String suffix, Refresh m)
            throws SQLException {
        int updated = grower.update(
                String.format(RefreshInsert, suffix),
                m.idPhoto,
                (int) m.category,
                m.title,
                m.idUser,
                m.niceName,
                m.updateAt);
        return updated;

    }

    public static int deleteRefresh(ForestGrower grower, String suffix, long idPhoto)
            throws SQLException {
        int updated = grower.update(String.format(RefreshDelete, suffix), idPhoto);
        return updated;

    }

    /**
     * delete less than at (seconds)
     *
     * @param grower
     * @param suffix
     * @param at
     * @return
     * @throws SQLException
     */
    public static int deleteRefreshByAt(ForestGrower grower, String suffix, int at)
            throws SQLException {
        int updated = grower.update(String.format(RefreshDeleteBeforeAt, suffix), at);
        return updated;

    }

    public static int addChoice(ForestGrower grower, String suffix, Choice m) throws SQLException {
        Map<String, Object> one = grower.selectOne(String.format(ChoiceExists, suffix), m.idPhoto);
        if (one == null) {
            int updated = grower.update(
                    String.format(ChoiceInsert, suffix),
                    m.idPhoto,
                    (int) m.category,
                    m.title,
                    m.idUser,
                    m.niceName,
                    m.updateAt);
            return updated;
        } else {
            return 0;
        }

    }

    public static int deleteChoice(ForestGrower grower, String suffix, long idPhoto)
            throws SQLException {
        int updated = grower.update(String.format(ChoiceDelete, suffix), idPhoto);
        return updated;

    }

    /**
     * delete less than at (seconds)
     *
     * @param grower
     * @param suffix
     * @param at
     * @return
     * @throws SQLException
     */
    public static int deleteChoiceByAt(ForestGrower grower, String suffix, int at)
            throws SQLException {
        int updated = grower.update(String.format(ChoiceDeleteBeforeAt, suffix), at);
        return updated;

    }

}
