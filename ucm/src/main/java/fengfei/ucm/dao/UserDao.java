package fengfei.ucm.dao;

import java.sql.SQLException;
import java.util.List;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.LongTransducer;
import fengfei.forest.database.dbutils.StringTransducer;
import fengfei.ucm.dao.transducer.UserTransducer;
import fengfei.ucm.profile.entity.User;
import fengfei.ucm.profile.entity.UserPwd;

public class UserDao {

	public static int saveUserPwd(ForestGrower grower, String suffix,
			UserPwd userPwd) throws SQLException {

		String insert = "INSERT INTO user_pwd" + suffix
				+ "( email, username, password) VALUES ( ?,?,?)";
		int updated = grower.update(insert, userPwd.getEmail(),
				userPwd.getUserName(), userPwd.getPassword());

		return updated;
	}

	public static int updatePassword(ForestGrower grower, String suffix,
			UserPwd userPwd) throws SQLException {
		String update = "update user_pwd" + suffix
				+ " set password=?   where username=? or email=? or id_user=?";
		int updated = grower.update(update, userPwd.getPassword(),
				userPwd.getUserName(), userPwd.getEmail(), userPwd.getIdUser());
		return updated;
	}

	public static boolean isExists(ForestGrower grower, String suffix,
			UserPwd userPwd) throws SQLException {
		String sql = "select id_user from user_pwd" + suffix
				+ " where  username=? or email=?  ";
		String id = grower.selectOne(sql, new StringTransducer(),
				userPwd.getEmail(), userPwd.getUserName());
		return id != null && !"".equals(id);
	}

	public static User getUser(ForestGrower grower, String suffix, int idUser)
			throws SQLException {
		String sql = "SELECT id_user, first_name, last_name, birthday, gender, phone_num, location FROM user"
				+ suffix + " where id_user=?";
		User user = grower.selectOne(sql, new UserTransducer(), idUser);
		UserPwd pwd = getUserPwd(grower, suffix, idUser);
		user.setEmail(pwd.getEmail());
		user.setUserName(pwd.getUserName());
		return user;
	}

	public static UserPwd getUserPwd(ForestGrower grower, String suffix,
			int idUser) throws SQLException {
		String sql = "SELECT id_user, email, username FROM user_pwd" + suffix
				+ " where id_user=?";
		UserPwd user = grower.selectOne(sql, new UserTransducer(), idUser);
		return user;
	}

	public static UserPwd getUserPwd(ForestGrower grower, String suffix, String email,
			String pwd) throws SQLException {
		String sql = "SELECT id_user, email, username FROM user_pwd" + suffix
				+ " where email=? and password=?";
		UserPwd user = grower.selectOne(sql, new UserTransducer(), email, pwd);
		return user;
	}

	public static int saveUser(ForestGrower grower, String suffix, User user)
			throws SQLException {

		String insert = "INSERT INTO user"
				+ suffix
				+ "(first_name, last_name, birthday, gender, phone_num, location) VALUES ( ?,?,?,?,?,?)";
		int updated = grower.update(insert, user.getFirstName(),
				user.getLastName(), user.getBirthday(), user.getGender(),
				user.getPhoneNum(), user.getLocation());

		return updated;
	}

	public static int updateUserById(ForestGrower grower, String suffix,
			User user) throws SQLException {

		String update = "update user_pwd"
				+ suffix
				+ "SET first_name = '? , last_name =?, birthday =?, gender = ?, phone_num = ?, location = ? "
				+ " where  id_user=?";
		int updated = grower.update(update, user.getFirstName(),
				user.getLastName(), user.getBirthday(), user.getGender(),
				user.getPhoneNum(), user.getLocation(), user.getIdUser());
		return updated;
	}

	public static int updateUserByEmail(ForestGrower grower, String suffix,
			User user) throws SQLException {
		String sql = "select id_user from user_pwd" + suffix
				+ " where email=?  ";
		String update = "update user_pwd"
				+ suffix
				+ "SET first_name = '? , last_name =?, birthday =?, gender = ?, phone_num = ?, location = ? "
				+ " where id_user=(" + sql + ")";
		int updated = grower.update(update, user.getFirstName(),
				user.getLastName(), user.getBirthday(), user.getGender(),
				user.getPhoneNum(), user.getLocation(), user.getEmail());
		return updated;
	}

	public static int updateUserByUserName(ForestGrower grower, String suffix,
			User user) throws SQLException {
		String sql = "select id_user from user_pwd" + suffix
				+ " where  username=?  ";
		String update = "update user_pwd"
				+ suffix
				+ "SET first_name = '? , last_name =?, birthday =?, gender = ?, phone_num = ?, location = ? "
				+ " where username=(" + sql + ") ";
		int updated = grower.update(update, user.getFirstName(),
				user.getLastName(), user.getBirthday(), user.getGender(),
				user.getPhoneNum(), user.getLocation(), user.getUserName());
		return updated;
	}

	public static int saveUserPhotograph(ForestGrower grower, String suffix,
			int idUser, long idPhoto) throws SQLException {
		String sql = "INSERT INTO user_photograph" + suffix
				+ "(idUser, idPhoto) VALUES (?, ?)";
		int updated = grower.update(sql, idUser, idPhoto);
		return updated;
	}

	public static List<Long> getUserPhotograph(ForestGrower grower,
			String suffix, int idUser) throws SQLException {
		String sql = "select idPhoto from user_photograph" + suffix
				+ " where idUser=? ";
		List<Long> idPhotos = grower.select(sql, new LongTransducer(), idUser);
		return idPhotos;
	}

}
