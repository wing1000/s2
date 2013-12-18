package fengfei.ucm.dao;

import fengfei.fir.utils.AppUtils;
import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.KeyValueTransducer;
import fengfei.forest.database.dbutils.StringTransducer;
import fengfei.forest.database.dbutils.impl.ForestRunner.InsertResultSet;
import fengfei.ucm.dao.transducer.IntegerTransducer;
import fengfei.ucm.dao.transducer.UserPwdTransducer;
import fengfei.ucm.dao.transducer.UserTransducer;
import fengfei.ucm.entity.profile.User;
import fengfei.ucm.entity.profile.UserPwd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserDao {

    public static int saveUserPwd(ForestGrower grower, String suffix, UserPwd userPwd)
        throws SQLException {

        String insert = "INSERT INTO user_pwd" + suffix
                + "( email, username, password) VALUES ( ?,?,?)";
        int updated = grower.update(
            insert,
            userPwd.getEmail(),
            userPwd.getUserName(),
            userPwd.getPassword());

        return updated;
    }

    public static InsertResultSet<Integer> addUserPwd(
        ForestGrower grower,
        String suffix,
        UserPwd userPwd) throws SQLException {

        String insert = "INSERT INTO user_pwd" + suffix
                + " ( email, username, password,create_at,update_at) VALUES ( ?,?,?,?,?)";

        InsertResultSet<Integer> rs = grower.insertForInt(
            insert,
            userPwd.getEmail(),
            userPwd.getUserName(),
            userPwd.getPassword(),
            userPwd.createAt,
            userPwd.updateAt);
        return rs;
    }

    public static
        int
        updatePassword(ForestGrower grower, String suffix, int idUser, String newPwd)
            throws SQLException {
        String update = "update user_pwd" + suffix + " set password=?   where   id_user=?";
        int updated = grower.update(update, newPwd, idUser);
        return updated;
    }

    public static boolean isExists(ForestGrower grower, String suffix, UserPwd userPwd)
        throws SQLException {
        String sql = "select id_user from user_pwd" + suffix + " where  username=? or email=?  ";
        String id = grower.selectOne(
            sql,
            new StringTransducer(),
            userPwd.getEmail(),
            userPwd.getUserName());
        return id != null && !"".equals(id);
    }

    public static boolean isExistsForEmail(ForestGrower grower, String suffix, String email)
        throws SQLException {
        String sql = "select id_user from user_pwd" + suffix + " where    email=?  ";
        String id = grower.selectOne(sql, new StringTransducer(), email);
        return id != null && !"".equals(id);
    }

    public static boolean isExistsForUserName(ForestGrower grower, String suffix, String username)
        throws SQLException {
        String sql = "select id_user from user_pwd" + suffix + " where username=?  ";
        String id = grower.selectOne(sql, new StringTransducer(), username);
        return id != null && !"".equals(id);
    }


    public static UserPwd getUserPwd(ForestGrower grower, String suffix, int idUser)
        throws SQLException {
        String sql = "SELECT id_user, email, username FROM user_pwd" + suffix + " where id_user=?";
        UserPwd user = grower.selectOne(sql, new UserPwdTransducer(), idUser);
        return user;
    }

    public static
        UserPwd
        getUserPwdByUserName(ForestGrower grower, String suffix, String userName)
            throws SQLException {
        String sql = "SELECT id_user, email, username FROM user_pwd" + suffix
                + " where username=?";
        UserPwd user = grower.selectOne(sql, new UserPwdTransducer(), userName);
        return user;
    }

    public static List<Integer> searchUserIdsByUserName(
        ForestGrower grower,
        String suffix,
        String userName,
        int offset,
        int limit) throws SQLException {
        String sql = "SELECT id_user, email, username FROM user_pwd" + suffix
                + " where username like ? limit ?,?";
        List<Integer> users = grower.select(
            sql,
            new IntegerTransducer(),
            "%" + userName + "%",
            offset,
            limit);
        return users;
    }

    public static
        Map<String, Object>
        getFullUserPwd(ForestGrower grower, String suffix, int idUser) throws SQLException {
        String sql = "SELECT id_user, email, username,password FROM user_pwd" + suffix
                + " where id_user=?";
        Map<String, Object> user = grower.selectOne(sql, idUser);
        return user;
    }
    ///

    public static User getUser(ForestGrower grower, String suffix, int idUser) throws SQLException {
        String sql = "SELECT id_user, real_name, nice_name, birthday, gender, phone_num, country, state, city, about,head_photo,create_at,update_at,count_view,count_vote,count_favorite,count_comment,count_affection  FROM user"
                + suffix + " where id_user=?";
        User user = grower.selectOne(sql, new UserTransducer(), idUser);

        return user;
    }
    public static Map<Integer, User> selectUsers(
        ForestGrower grower,
        String suffix,
        Collection<? extends Number> idUsers) throws SQLException {
        String sql = "SELECT id_user, real_name, nice_name, birthday, gender, phone_num, country, state, city, about,head_photo,create_at,update_at,count_view,count_vote,count_favorite,count_comment,count_affection "
                + " FROM user%s where id_user in (%s)";
        String inCause = AppUtils.toInCause(idUsers);
        Map<Integer, User> users = grower.selectMap(
            String.format(sql, suffix, inCause),
            new KeyValueTransducer<Integer, User>() {

                UserTransducer transducer = new UserTransducer();

                @Override
                public
                    fengfei.forest.database.dbutils.KeyValueTransducer.KeyValue<Integer, User>
                    transform(ResultSet rs) throws SQLException {
                    User user = transducer.transform(rs);
                    KeyValue<Integer, User> kv = new KeyValue<Integer, User>(user.idUser, user);
                    return kv;
                }
            });
        return users;
    }

    public static Map<Integer, User> selectFullUsers(
        ForestGrower grower,
        String suffix,
        Collection<? extends Number> idUsers,
        final Map<Integer, User> users) throws SQLException {
        String sql = "SELECT id_user, email, username FROM user_pwd%s where id_user  in (%s)";

        String inCause = AppUtils.toInCause(idUsers);
        Map<Integer, User> userMaps = grower.selectMap(
            String.format(sql, suffix, inCause),
            new KeyValueTransducer<Integer, User>() {

                UserPwdTransducer transducer = new UserPwdTransducer();

                @Override
                public
                    fengfei.forest.database.dbutils.KeyValueTransducer.KeyValue<Integer, User>
                    transform(ResultSet rs) throws SQLException {
                    UserPwd up = transducer.transform(rs);

                    User user = users.get(up.idUser);
                    if (user == null) {
                        user = new User();
                        user.niceName = up.userName;
                    }
                    user.email = up.email;
                    user.userName = up.userName;

                    KeyValue<Integer, User> kv = new KeyValue<Integer, User>(user.idUser, user);
                    return kv;
                }
            });
        return userMaps;
    }

    public static List<User> selectUserList(
        ForestGrower grower,
        String suffix,
        Collection<? extends Number> idUsers) throws SQLException {
        String sql = "SELECT id_user, real_name, nice_name, birthday, gender, phone_num, country, state, city, about,head_photo ,create_at,update_at,count_view,count_vote,count_favorite,count_comment,count_affection "
                + "FROM user%s where id_user in (%s)";
        String inCause = AppUtils.toInCause(idUsers);
        List<User> users = grower
            .select(String.format(sql, suffix, inCause), new UserTransducer());
        return users;
    }

    public static UserPwd getUserPwd(
        ForestGrower grower,
        String suffix,
        String emailOrName,
        String pwd) throws SQLException {
        String sql = "SELECT id_user, email, username FROM user_pwd" + suffix
                + " where (email=? or username=?) and password=?";
        UserPwd user = grower.selectOne(
            sql,
            new UserPwdTransducer(),
            emailOrName,
            emailOrName,
            pwd);
        return user;
    }

    // //
    public static int saveUser(ForestGrower grower, String suffix, User user) throws SQLException {

        String insert = "INSERT INTO user"
                + suffix
                + "(id_user,real_name, nice_name, birthday, gender, phone_num, country, state, city, about,create_at,update_at)"
                + "  VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        int updated = grower.update(
            insert,
            user.getIdUser(),
            user.getRealName(),
            user.getNiceName(),
            user.getBirthday(),
            user.getGender(),
            user.getPhoneNum(),
            user.getCountry(),
            user.getState(),
            user.getCity(),
            user.getAbout(),
            user.createAt,
            user.updateAt);

        return updated;
    }

    public static int updateUser(ForestGrower grower, String suffix, User user)
        throws SQLException {
        String update = "UPDATE user"
                + suffix
                + " SET real_name = ?,nice_name=?, birthday = ?, "
                + "gender = ?, phone_num = ?, country = ?, state = ?, city = ?, about = ?,update_at=? where id_user=? ";

        int updated = grower.update(
            update,
            user.getRealName(),
            user.getNiceName(),
            user.getBirthday(),
            user.getGender(),
            user.getPhoneNum(),
            user.getCountry(),
            user.getState(),
            user.getCity(),
            user.getAbout(),
            user.updateAt,
            user.getIdUser());

        return updated;
    }

    public static int updateUserById(ForestGrower grower, String suffix, User user)
        throws SQLException {

        String update = "UPDATE user"
                + suffix
                + " SET real_name = ? ,nice_name=?, birthday = ?, "
                + "gender = ?, phone_num = ?, country = ?, state = ?, city = ?, about = ? where id_user=? ";

        int updated = grower.update(
            update,
            user.getRealName(),
            user.getNiceName(),
            user.getBirthday(),
            user.getGender(),
            user.getPhoneNum(),
            user.getCountry(),
            user.getState(),
            user.getCity(),
            user.getAbout(),
            user.getIdUser());
        return updated;
    }

    public static int saveUserHeadPhoto(ForestGrower grower, String suffix, User user)
        throws SQLException {

        String insert = "INSERT INTO user" + suffix + "(id_user, create_at,update_at,head_photo)"
                + "  VALUES (?,?,?,?)";
        int updated = grower.update(
            insert,
            user.getIdUser(),
            user.createAt,
            user.updateAt,
            user.isHeadPhoto ? 1 : 0);

        return updated;
    }

    public static int updateHeadPhoto(
        ForestGrower grower,
        String suffix,
        int idUser,
        boolean isHeadPhoto) throws SQLException {
        String update = "UPDATE user" + suffix + " SET head_photo = ? where id_user=? ";
        int updated = grower.update(update, isHeadPhoto ? 1 : 0, idUser);
        return updated;
    }

    public static int updateUserByEmail(ForestGrower grower, String suffix, User user)
        throws SQLException {
        String sql = "select id_user from user_pwd" + suffix + " where email=?  ";
        String update = "update user_pwd"
                + suffix
                + "SET real_name = '? , nice_name=?,birthday =?, gender = ?, phone_num = ?, country = ?, state = ?, city = ?, about = ? "
                + " where id_user=(" + sql + ")";
        int updated = grower.update(
            update,
            user.getRealName(),
            user.getNiceName(),
            user.getBirthday(),
            user.getGender(),
            user.getPhoneNum(),
            user.getCountry(),
            user.getState(),
            user.getCity(),
            user.getAbout(),
            user.getEmail());
        return updated;
    }

    public static int updateUserByUserName(ForestGrower grower, String suffix, User user)
        throws SQLException {
        String sql = "select id_user from user_pwd" + suffix + " where  username=?  ";
        String update = "update user_pwd"
                + suffix
                + "SET real_name = '? , nice_name=?,birthday =?, gender = ?, phone_num = ?, country = ?, state = ?, city = ?, about = ? "
                + " where username=(" + sql + ") ";
        int updated = grower.update(
            update,
            user.getRealName(),
            user.getNiceName(),
            user.getBirthday(),
            user.getGender(),
            user.getPhoneNum(),
            user.getCountry(),
            user.getState(),
            user.getCity(),
            user.getAbout(),
            user.getUserName());
        return updated;
    }

    // /

}
