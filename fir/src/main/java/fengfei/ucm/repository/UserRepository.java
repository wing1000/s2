package fengfei.ucm.repository;

import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.profile.User;
import fengfei.ucm.entity.profile.UserPwd;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends UnitNames {

    int addUserPwd(UserPwd userPwd) throws Exception;

    int saveUserPwd(UserPwd userPwd) throws Exception;

    boolean isExists(UserPwd userPwd) throws Exception;

    User getUser(Integer idUser) throws Exception;

    User getUserByUserName(String userName) throws Exception;

    UserPwd getUserPwd(Integer idUser) throws Exception;

    UserPwd getUserPwd(String emailOrName, String pwd) throws Exception;

    public UserPwd getUserPwd(final String userName) throws Exception;

    // /
    int saveUser(User user) throws Exception;

    int updatePassword(Integer idUser, String oldPwd, String newPwd) throws Exception;

    int updateUserById(User user) throws Exception;

    int updateUserByEmail(User user) throws Exception;

    int updateUserByUserName(User user) throws Exception;

    boolean updateHeadPhoto(Integer idUser, boolean isHeadPhoto) throws Exception;

    List<User> selectUserList(final Collection<? extends Number> idUsers)
        throws Exception;

    User getFullUser(final Integer idUser) throws Exception;

    Map<Integer, User> selectUsers(Collection<? extends Number> idUsers)
        throws Exception;
}
