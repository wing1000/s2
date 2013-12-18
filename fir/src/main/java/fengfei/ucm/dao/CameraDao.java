package fengfei.ucm.dao;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.ucm.dao.transducer.CameraTransducer;
import fengfei.ucm.entity.profile.Camera;

import java.sql.SQLException;
import java.util.List;

public class CameraDao {

    final static String Insert = "INSERT INTO user_camera%s(id_user, equipment, type) VALUES (?, ?, ?)";
    final static String Delete = "delete from user_camera%s where id_user=?";
    final static String DeleteOne = "delete from user_camera%s where id_user=? and id_camera=?";

    public static int[] add(ForestGrower grower, String suffix, List<Camera> models)
            throws SQLException {
        String insert = String.format(Insert, suffix);
        Object[][] params = new Object[models.size()][];
        for (int i = 0; i < params.length; i++) {
            Camera m = models.get(i);
            params[i] = new Object[]{m.getIdUser(), m.getEquipment(), m.getType()};
        }

        int[] updated = grower.batchUpdate(insert, params);
        // List<InsertResultSet<Long>> insertResultSets = new ArrayList<>();
        //
        // for (CameraModel exifModel : models) {
        // InsertResultSet<Long> u = addOne(grower, suffix, exifModel);
        // insertResultSets.add(u);
        // }

        return updated;
    }

    public static int addOne(ForestGrower grower, String suffix, Camera m) throws SQLException {

        String insert = String.format(Insert, suffix);
        String delete = String.format(Delete, suffix);
        int deleted = grower.update(delete, m.getIdUser());
        int updated = grower.update(insert, m.getIdUser(), m.getEquipment(), m.getType());
        return updated;

    }

    public static int deleteByUser(ForestGrower grower, String suffix, int idUser)
            throws SQLException {

        String delete = String.format(Delete, suffix);
        int deleted = grower.update(delete, idUser);
        return deleted;

    }

    public static int deleteOneByUserAndIdCamera(ForestGrower grower, String suffix, int idUser, long idCamera)
            throws SQLException {

        String delete = String.format(DeleteOne, suffix);
        int deleted = grower.update(delete, idUser, idCamera);
        return deleted;

    }

    public static List<Camera> select(ForestGrower grower, String suffix, int idUser)
            throws SQLException {
        String sql = "SELECT id_user, equipment, type, id_camera FROM user_camera%s WHERE id_user=?";
        List<Camera> ms = grower
                .select(String.format(sql, suffix), new CameraTransducer(), idUser);
        return ms;
    }

    public static ListMultimap<String, Camera> selectGroup(
            ForestGrower grower,
            String suffix,
            int idUser) throws SQLException {
        ListMultimap<String, Camera> multimap = ArrayListMultimap.create();
        String sql = "SELECT id_user, equipment, type, id_camera FROM user_camera%s WHERE id_user=?";
        List<Camera> ms = grower
                .select(String.format(sql, suffix), new CameraTransducer(), idUser);
        for (Camera m : ms) {
            multimap.put(m.getType(), m);
        }

        return multimap;
    }

}
