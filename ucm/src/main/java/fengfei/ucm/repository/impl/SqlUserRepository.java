package fengfei.ucm.repository.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.slice.SliceResource.Function;
import fengfei.forest.slice.database.PoolableDatabaseResource;
import fengfei.ucm.dao.DataAccessException;
import fengfei.ucm.dao.Transactions;
import fengfei.ucm.dao.Transactions.TaCallback;
import fengfei.ucm.dao.Transactions.TransactionCallback;
import fengfei.ucm.dao.UserDao;
import fengfei.ucm.profile.entity.User;
import fengfei.ucm.profile.entity.UserPwd;
import fengfei.ucm.repository.UserRepository;

@Repository
public class SqlUserRepository implements UserRepository {

	@Override
	public int saveUserPwd(final UserPwd userPwd) throws DataAccessException {

		TransactionCallback<Integer> callback = new TransactionCallback<Integer>() {

			@Override
			public Integer execute(ForestGrower grower,
					PoolableDatabaseResource resource) throws SQLException {
				String suffix = resource.getAlias();
				suffix = "";
				return UserDao.saveUserPwd(grower, suffix, userPwd);
			}
		};
		return Transactions.execute(UserUnitName, UserPwdSliceId,
				Function.Write, callback);
	}

	@Override
	public boolean isExists(final UserPwd userPwd) throws DataAccessException {

		TransactionCallback<Boolean> callback = new TransactionCallback<Boolean>() {

			@Override
			public Boolean execute(ForestGrower grower,
					PoolableDatabaseResource resource) throws SQLException {
				String suffix = resource.getAlias();
				suffix = "";
				return UserDao.isExists(grower, suffix, userPwd);
			}
		};
		return Transactions.execute(UserUnitName, UserPwdSliceId,
				Function.Write, callback);
	}

	@Override
	public UserPwd getUserPwd(final int idUser) throws DataAccessException {

		TaCallback<UserPwd> callback = new TaCallback<UserPwd>() {

			@Override
			public UserPwd execute(ForestGrower grower, String suffix)
					throws SQLException {
				suffix = "";
				return UserDao.getUserPwd(grower, suffix, idUser);
			}

		};
		return Transactions.execute(UserUnitName, UserPwdSliceId,
				Function.Write, callback);
	}

	public UserPwd getUserPwd(final String email, final String pwd)
			throws DataAccessException {

		TaCallback<UserPwd> callback = new TaCallback<UserPwd>() {

			@Override
			public UserPwd execute(ForestGrower grower, String suffix)
					throws SQLException {
				suffix = "";
				return UserDao.getUserPwd(grower, suffix, email, pwd);
			}

		};
		return Transactions.execute(UserUnitName, UserPwdSliceId,
				Function.Write, callback);
	}

	@Override
	public int updatePassword(final UserPwd userPwd) throws DataAccessException {

		TaCallback<Integer> callback = new TaCallback<Integer>() {

			@Override
			public Integer execute(ForestGrower grower, String suffix)
					throws SQLException {
				suffix = "";
				return UserDao.updatePassword(grower, suffix, userPwd);
			}

		};
		return Transactions.execute(UserUnitName, UserPwdSliceId,
				Function.Write, callback);
	}

	// -------------------------------------User
	@Override
	public User getUser(final int idUser) throws DataAccessException {

		TaCallback<User> callback = new TaCallback<User>() {

			@Override
			public User execute(ForestGrower grower, String suffix)
					throws SQLException {
				return UserDao.getUser(grower, suffix, idUser);
			}

		};
		return Transactions.execute(ExifUnitName, idUser, Function.Write,
				callback);
	}

	@Override
	public int saveUser(final User user) throws DataAccessException {

		TaCallback<Integer> callback = new TaCallback<Integer>() {

			@Override
			public Integer execute(ForestGrower grower, String suffix)
					throws SQLException {
				return UserDao.saveUser(grower, suffix, user);
			}

		};
		return Transactions.execute(ExifUnitName, user.idUser, Function.Write,
				callback);
	}

	@Override
	public int updateUserById(final User user) throws DataAccessException {

		TaCallback<Integer> callback = new TaCallback<Integer>() {

			@Override
			public Integer execute(ForestGrower grower, String suffix)
					throws SQLException {
				return UserDao.updateUserById(grower, suffix, user);
			}

		};
		return Transactions.execute(ExifUnitName, user.idUser, Function.Write,
				callback);
	}

	@Override
	public int updateUserByEmail(final User user) throws DataAccessException {

		TaCallback<Integer> callback = new TaCallback<Integer>() {

			@Override
			public Integer execute(ForestGrower grower, String suffix)
					throws SQLException {
				return UserDao.updateUserByEmail(grower, suffix, user);
			}

		};
		return Transactions.execute(ExifUnitName, user.idUser, Function.Write,
				callback);
	}

	@Override
	public int updateUserByUserName(final User user) throws DataAccessException {

		TaCallback<Integer> callback = new TaCallback<Integer>() {

			@Override
			public Integer execute(ForestGrower grower, String suffix)
					throws SQLException {
				return UserDao.updateUserByUserName(grower, suffix, user);
			}

		};
		return Transactions.execute(ExifUnitName, user.idUser, Function.Write,
				callback);
	}

	// --------------------------photo
	@Override
	public int saveUserPhotograph(final int idUser, final long idPhoto)
			throws DataAccessException {

		TaCallback<Integer> callback = new TaCallback<Integer>() {

			@Override
			public Integer execute(ForestGrower grower, String suffix)
					throws SQLException {
				return UserDao.saveUserPhotograph(grower, suffix, idUser,
						idPhoto);
			}

		};
		return Transactions.execute(ExifUnitName, idUser, Function.Write,
				callback);
	}

	@Override
	public List<Long> getUserPhotograph(final int idUser)
			throws DataAccessException {

		TaCallback<List<Long>> callback = new TaCallback<List<Long>>() {

			@Override
			public List<Long> execute(ForestGrower grower, String suffix)
					throws SQLException {
				return UserDao.getUserPhotograph(grower, suffix, idUser);
			}

		};
		return Transactions.execute(ExifUnitName, idUser, Function.Write,
				callback);
	}
}
