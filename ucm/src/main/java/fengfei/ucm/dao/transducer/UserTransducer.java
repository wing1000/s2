package fengfei.ucm.dao.transducer;

import java.sql.ResultSet;
import java.sql.SQLException;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.profile.entity.User;

public class UserTransducer implements Transducer<User> {

	@Override
	public User transform(ResultSet rs) throws SQLException {
		long idUser = rs.getLong("id_user");
		String email = rs.getString("email");
		String userName = rs.getString("username");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		String birthday = rs.getString("birthday ");
		String gender = rs.getString("gender");
		String phoneNum = rs.getString("phone_num");
		String location = rs.getString("location");

		return new User(idUser, userName, email, firstName, lastName, birthday,
				gender, phoneNum, location);
	}

}
