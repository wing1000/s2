package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.LongTransducer;
import fengfei.forest.database.dbutils.StringTransducer;
import fengfei.ucm.dao.transducer.IntegerTransducer;

import java.sql.SQLException;

public class UserConfigDao {

    final static String Insert = "insert into user_config%s(id_user,%s) values(?,?)";
    final static String UpdateIncr = "update user_config%s set %s=%s+? where id_user=?";
    final static String Update = "update user_config%s set %s=? where id_user=?";
    final static String SelectForLock = "select id_user from user_config%s where id_user=? for update";
    final static String SelectOne = "select %s from user_config%s where id_user=? ";

    public static int updateDirLimit(ForestGrower grower, String suffix,
                                     Integer idUser, int value) throws SQLException {
        return incrInt(grower, suffix, "dir_size", idUser, value);

    }

    public static int incrInt(ForestGrower grower, String suffix, String field,
                              Integer idUser, int value) throws SQLException {
        Integer oldIdUser = grower.selectOne(
                String.format(SelectForLock, suffix), new IntegerTransducer(),
                idUser);
        if (oldIdUser == null) {
            int updated = grower.update(String.format(Insert, suffix, field),
                    idUser, value < 0 ? 0 : 1);
            return updated;
        } else {
            int updated = grower.update(
                    String.format(UpdateIncr, suffix, field, field), value, idUser);
            return updated;
        }

    }

    public static int setValue(ForestGrower grower, String suffix, String field,
                               Integer idUser, Object value) throws SQLException {
        Integer oldIdUser = grower.selectOne(
                String.format(SelectForLock, suffix), new IntegerTransducer(),
                idUser);
        if (oldIdUser == null) {
            int updated = grower.update(String.format(Insert, suffix, field),
                    idUser, value);
            return updated;
        } else {
            int updated = grower.update(
                    String.format(Update, suffix, field, field), value, idUser);
            return updated;
        }

    }

    public static Integer getIntValue(ForestGrower grower, String suffix,
                                      String field, Integer idUser) throws SQLException {
        Integer value = grower.selectOne(
                String.format(SelectOne, field, suffix),
                new IntegerTransducer(), idUser);
        return value;
    }

    public static Long getLongValue(ForestGrower grower, String suffix,
                                    String field, Integer idUser) throws SQLException {
        Long value = grower.selectOne(
                String.format(SelectOne, field, suffix),
                new LongTransducer(), idUser);
        return value;
    }

    public static String getStringValue(ForestGrower grower, String suffix,
                                        String field, Integer idUser) throws SQLException {
        String value = grower.selectOne(
                String.format(SelectOne, field, suffix),
                new StringTransducer(), idUser);
        return value;
    }

    public static int getDirLimit(ForestGrower grower, String suffix,
                                  Integer idUser) throws SQLException {
        Integer limit = getIntValue(grower, suffix, "dir_size", idUser);
        return limit == null ? 0 : limit;
    }
}

