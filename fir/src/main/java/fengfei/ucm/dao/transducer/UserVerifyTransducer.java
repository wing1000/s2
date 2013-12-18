package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.profile.UserVerify;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserVerifyTransducer implements Transducer<UserVerify> {

    @Override
    public UserVerify transform(ResultSet rs) throws SQLException {
        Integer idUser = rs.getInt("id_user");
        int createAt = rs.getInt("create_at");
        String verify = rs.getString("verify");

        UserVerify uv = new UserVerify();
        uv.idUser = idUser;
        uv.createAt = createAt;
        uv.verify = verify;
        return uv;
    }

}
