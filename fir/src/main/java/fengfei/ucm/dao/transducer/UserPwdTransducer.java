package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.profile.UserPwd;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPwdTransducer implements Transducer<UserPwd> {

    @Override
    public UserPwd transform(ResultSet rs) throws SQLException {
        Integer idUser = rs.getInt("id_user");
        String email = rs.getString("email");
        String userName = rs.getString("username");
        // String password = rs.getString("password");

        return new UserPwd(idUser, userName, email, null);
    }

}
