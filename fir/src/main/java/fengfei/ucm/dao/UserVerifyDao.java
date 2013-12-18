package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.ucm.dao.transducer.UserVerifyTransducer;
import fengfei.ucm.entity.profile.UserVerify;

import java.sql.SQLException;

public class UserVerifyDao {

    static String Insert = "insert into  user_verify%s (id_user,verify,create_at) values(?,?,?)";
    static String Select = "select id_user,verify,create_at from user_verify%s where id_user=?";

    public static int add(
        ForestGrower grower,
        String suffix,
        Integer idUser,
        String verify,
        long createAt) throws SQLException {

        int updated = grower.update(String.format(Insert, suffix), idUser, verify, createAt);
        return updated;
    }

    public static UserVerify get(ForestGrower grower, String suffix, Integer idUser)
        throws SQLException {

        UserVerify uv = grower.selectOne(
            String.format(Select, suffix),
            new UserVerifyTransducer(),
            idUser);
        return uv;
    }
}
