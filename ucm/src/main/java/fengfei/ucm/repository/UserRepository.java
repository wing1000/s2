package fengfei.ucm.repository;

import org.springframework.stereotype.Repository;

import fengfei.ucm.dao.DataAccessException;
import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.profile.entity.User;
import fengfei.ucm.profile.entity.UserPwd;

@Repository
public interface UserRepository extends UnitNames {
	int saveUserPwd(UserPwd userPwd) throws DataAccessException;

	boolean isExists(UserPwd userPwd) throws DataAccessException;

	User getUser(int id) throws DataAccessException;

	UserPwd getUserPwd(int id) throws DataAccessException;

	UserPwd getUserPwd(String email, String pwd) throws DataAccessException;

	int saveUser(User user) throws DataAccessException;

	int updatePassword(UserPwd userPwd) throws DataAccessException;

	int updateUserById(User user) throws DataAccessException;

	int updateUserByEmail(User user) throws DataAccessException;

	int updateUserByUserName(User user) throws DataAccessException;

	int saveUserPhotograph(int idUser, long idPhoto) throws DataAccessException;

	java.util.List<Long> getUserPhotograph(int idUser) throws DataAccessException;

}
