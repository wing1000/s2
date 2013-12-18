package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.profile.Camera;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author tietang
 * 
 */
public class CameraTransducer implements Transducer<Camera> {

    @Override
    public Camera transform(ResultSet rs) throws SQLException {
        int idUser = rs.getInt("id_user");
        long idCamera = rs.getLong("id_camera");
        String equipment = rs.getString("equipment");
        String type = rs.getString("type");
        return new Camera(idUser, idCamera, equipment, type);
    }

}
