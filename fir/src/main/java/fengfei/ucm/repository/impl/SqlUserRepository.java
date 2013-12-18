package fengfei.ucm.repository.impl;

import fengfei.fir.utils.AppUtils;
import fengfei.fir.utils.Path;
import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.impl.ForestRunner.InsertResultSet;
import fengfei.forest.slice.SliceResource.Function;
import fengfei.forest.slice.database.PoolableDatabaseResource;
import fengfei.forest.slice.database.utils.Transactions;
import fengfei.forest.slice.database.utils.Transactions.TaCallback;
import fengfei.forest.slice.database.utils.Transactions.TransactionCallback;
import fengfei.ucm.dao.RankDao;
import fengfei.ucm.dao.UserDao;
import fengfei.ucm.dao.UserVerifyDao;
import fengfei.ucm.entity.profile.User;
import fengfei.ucm.entity.profile.UserPwd;
import fengfei.ucm.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;


@Repository
public class SqlUserRepository implements UserRepository {
    public final static String UserTableName = "user";

    @Override
    public int addUserPwd(final UserPwd userPwd) throws Exception {

        TransactionCallback<Integer> callback = new TransactionCallback<Integer>() {

            @Override
            public Integer execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                long curr = System.currentTimeMillis();
                userPwd.updateAt = curr;
                userPwd.createAt = (int) (curr / 1000);
                String suffix = resource.getAlias();
                suffix = "";
                boolean isUserNamed = UserDao.isExistsForUserName(
                        grower,
                        suffix,
                        userPwd.getUserName());
                boolean isEmailed = UserDao.isExistsForEmail(grower, suffix, userPwd.getEmail());
                if (isUserNamed && isEmailed) {
                    return -3;
                }
                if (isUserNamed) {
                    return -2;
                }
                if (isEmailed) {
                    return -1;
                }
                InsertResultSet<Integer> rs = UserDao.addUserPwd(grower, suffix, userPwd);
                if (rs.rows > 0) {
                    int update1 = RankDao.addUserRank(grower, suffix, rs.autoPk, userPwd.updateAt);
                    if (update1 > 0) {
                        UserVerifyDao.add(
                                grower,
                                suffix,
                                rs.autoPk,
                                userPwd.verify,
                                userPwd.createAt);
                        return rs.autoPk;
                    } else
                        throw new SQLException("add user stat error.");
                }
                return 0;

            }
        };
        return Transactions.execute(UserUnitName, UserPwdSliceId, Function.Write, callback);
    }

    @Override
    public int saveUserPwd(final UserPwd userPwd) throws Exception {

        TransactionCallback<Integer> callback = new TransactionCallback<Integer>() {

            @Override
            public Integer execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";
                return UserDao.saveUserPwd(grower, suffix, userPwd);
            }
        };
        return Transactions.execute(UserUnitName, UserPwdSliceId, Function.Write, callback);
    }

    @Override
    public boolean isExists(final UserPwd userPwd) throws Exception {

        TransactionCallback<Boolean> callback = new TransactionCallback<Boolean>() {

            @Override
            public Boolean execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";
                return UserDao.isExists(grower, suffix, userPwd);
            }
        };
        return Transactions.execute(UserUnitName, UserPwdSliceId, Function.Write, callback);
    }

    @Override
    public UserPwd getUserPwd(final Integer idUser) throws Exception {

        TaCallback<UserPwd> callback = new TaCallback<UserPwd>() {

            @Override
            public UserPwd execute(ForestGrower grower, String suffix) throws SQLException {
                suffix = "";
                return UserDao.getUserPwd(grower, suffix, idUser);
            }

        };
        return Transactions.execute(UserUnitName, UserPwdSliceId, Function.Write, callback);
    }

    @Override
    public UserPwd getUserPwd(final String userName) throws Exception {

        TaCallback<UserPwd> callback = new TaCallback<UserPwd>() {

            @Override
            public UserPwd execute(ForestGrower grower, String suffix) throws SQLException {
                suffix = "";
                return UserDao.getUserPwdByUserName(grower, suffix, userName);
            }

        };
        return Transactions.execute(UserUnitName, UserPwdSliceId, Function.Write, callback);
    }

    public UserPwd getUserPwd(final String emailOrName, final String pwd)
            throws Exception {

        TaCallback<UserPwd> callback = new TaCallback<UserPwd>() {

            @Override
            public UserPwd execute(ForestGrower grower, String suffix) throws SQLException {
                suffix = "";
                return UserDao.getUserPwd(grower, suffix, emailOrName, pwd);
            }

        };
        System.out.println(Transactions.get(UserUnitName,UserPwdSliceId));
        return Transactions.execute(UserUnitName, UserPwdSliceId, Function.Write, callback);
    }

    @Override
    public int updatePassword(final Integer idUser, final String oldPwd, final String newPwd)
            throws Exception {

        TaCallback<Integer> callback = new TaCallback<Integer>() {

            @Override
            public Integer execute(ForestGrower grower, String suffix) throws SQLException {
                suffix = "";
                Map<String, Object> user = UserDao.getFullUserPwd(grower, suffix, idUser);
                String pwd = user.get("password").toString();
                System.out.printf(
                        "old=%s,new=%s,pwd=%s,up=%s\n",
                        oldPwd,
                        newPwd,
                        pwd,
                        user.toString());

                if (user == null || !oldPwd.equals(pwd)) {
                    return -1;
                }
                return UserDao.updatePassword(grower, suffix, idUser, newPwd);
            }

        };
        return Transactions.execute(UserUnitName, UserPwdSliceId, Function.Write, callback);
    }

    // -------------------------------------User
    @Override
    public User getUser(final Integer idUser) throws Exception {

        TaCallback<User> callback = new TaCallback<User>() {

            @Override
            public User execute(ForestGrower grower, String suffix) throws SQLException {
                suffix = "";
                User user = UserDao.getUser(grower, suffix, idUser);

                return user;
            }

        };
        return Transactions.execute(UserUnitName, UserPwdSliceId, Function.Write, callback);
    }

    @Override
    public int saveUser(final User user) throws Exception {

        TaCallback<Integer> callback = new TaCallback<Integer>() {

            @Override
            public Integer execute(ForestGrower grower, String suffix) throws SQLException {
                suffix = "";
                User u = UserDao.getUser(grower, suffix, user.getIdUser());
                if (u == null) {
                    return UserDao.saveUser(grower, suffix, user);
                } else {
                    return UserDao.updateUser(grower, suffix, user);
                }

            }

        };
        return Transactions.execute(UserUnitName, UserPwdSliceId, Function.Write, callback);
    }

    @Override
    public int updateUserById(final User user) throws Exception {

        TaCallback<Integer> callback = new TaCallback<Integer>() {

            @Override
            public Integer execute(ForestGrower grower, String suffix) throws SQLException {
                return UserDao.updateUserById(grower, suffix, user);
            }

        };
        return Transactions.execute(UserUnitName, UserPwdSliceId, Function.Write, callback);
    }

    @Override
    public int updateUserByEmail(final User user) throws Exception {

        TaCallback<Integer> callback = new TaCallback<Integer>() {

            @Override
            public Integer execute(ForestGrower grower, String suffix) throws SQLException {
                return UserDao.updateUserByEmail(grower, suffix, user);
            }

        };
        return Transactions.execute(PhotoUnitName, UserPwdSliceId, Function.Write, callback);
    }

    @Override
    public int updateUserByUserName(final User user) throws Exception {

        TaCallback<Integer> callback = new TaCallback<Integer>() {

            @Override
            public Integer execute(ForestGrower grower, String suffix) throws SQLException {
                return UserDao.updateUserByUserName(grower, suffix, user);
            }

        };
        return Transactions.execute(PhotoUnitName, UserPwdSliceId, Function.Write, callback);
    }

    @Override
    public boolean updateHeadPhoto(final Integer idUser, final boolean isHeadPhoto)
            throws Exception {
        TaCallback<Integer> callback = new TaCallback<Integer>() {

            @Override
            public Integer execute(ForestGrower grower, String suffix) throws SQLException {
                suffix = "";
                User u = UserDao.getUser(grower, suffix, idUser);
                if (u == null) {
                    User user = new User();
                    user.idUser = idUser;
                    user.isHeadPhoto = isHeadPhoto;
                    return UserDao.saveUserHeadPhoto(grower, suffix, user);
                } else {
                    return UserDao.updateHeadPhoto(grower, suffix, idUser, isHeadPhoto);
                }
            }

        };
        int updated = Transactions.execute(UserUnitName, UserPwdSliceId, Function.Write, callback);
        return updated > 0;
    }

    public User getFullUser(final Integer idUser) throws Exception {

        User user = Transactions.execute(
                UserUnitName,
                UserPwdSliceId,
                Function.Read,
                new TaCallback<User>() {

                    @Override
                    public User execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = "";
                        UserPwd up = UserDao.getUserPwd(grower, "", idUser);
                        if (up == null) {
                            return null;
                        }
                        //
                        User user = UserDao.getUser(grower, suffix, up.idUser);
                        if (user == null) {
                            user = new User();
                        }
                        user.setUserPwd(up);

                        return user;
                    }

                });
        if (user != null) {
            user.niceName = AppUtils.toNiceName(user);
            user.headPath = Path.getHeadPhotoDownloadPath(user.idUser);
        }
        return user;

    }

    public User getUserByUserName(final String userName) throws Exception {

        User user = Transactions.execute(
                UserUnitName,
                UserPwdSliceId,
                Function.Read,
                new TaCallback<User>() {

                    @Override
                    public User execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = "";
                        UserPwd up = UserDao.getUserPwdByUserName(grower, suffix, userName);
                        if (up == null) {
                            return null;
                        }
                        //
                        User user = UserDao.getUser(grower, suffix, up.idUser);
                        if (user == null) {
                            user = new User();

                        }
                        user.setUserPwd(up);
                        return user;
                    }

                });
        if (user != null) {
            user.niceName = AppUtils.toNiceName(user);
            user.headPath = Path.getHeadPhotoDownloadPath(user.idUser);
        }
        return user;

    }

    public String getNiceName(int idUser) throws Exception {
        User user = getUser(idUser);

        return AppUtils.toNiceName(user);
    }

    @Override
    public List<User> selectUserList(final Collection<? extends Number> idUsers)
            throws Exception {

        List<User> users = Transactions.execute(
                UserUnitName,
                UserPwdSliceId,
                Function.Read,
                new TaCallback<List<User>>() {

                    @Override
                    public List<User> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";

                        return UserDao.selectUserList(grower, suffix, idUsers);
                    }

                });

        return users;
    }

    @Override
    public Map<Integer, User> selectUsers(final Collection<? extends Number> idUsers)
            throws Exception {


        Map<Integer, User> users = Transactions.execute(
                UserUnitName,
                UserPwdSliceId,
                Function.Read,
                new TaCallback<Map<Integer, User>>() {

                    @Override
                    public Map<Integer, User> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = "";
                        Map<Integer, User> users = UserDao.selectUsers(grower, suffix, idUsers);
                        return UserDao.selectFullUsers(grower, suffix, idUsers, users);
                    }

                });

        return users;
    }

}
