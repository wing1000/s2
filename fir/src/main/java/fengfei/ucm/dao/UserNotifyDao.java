package fengfei.ucm.dao;

import fengfei.fir.model.Notify;
import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.LongTransducer;

import java.sql.SQLException;
import java.util.List;

public class UserNotifyDao {

    final static String InsertNotify = "insert into user_config%s(id_user,`notice`) values(?,?)";
    final static String UpdateNotify = "update user_config%s set `notice`=? where id_user=? ";
    final static String ExsitsNotify = "select `notice` from user_config%s where id_user=? for update";

    public static int writeNotify(
            ForestGrower grower,
            String suffix,
            int idUser,
            List<Notify> notifies) throws SQLException {
        Long value = grower.selectOne(
                String.format(ExsitsNotify, suffix),
                new LongTransducer(),
                idUser);
        long v = 0;// value == null ? 0 : value.longValue();
        for (Notify notify : notifies) {
            if (notify.isNotify) {
                v |= 1 << (notify.position - 1);
            } else {
                v &= ~(1 << (notify.position - 1));
            }
        }

        System.out.println("notify: "+v);
        System.out.println("notify: "+notifies);
        int updated = 0;
        if (value == null) {
            updated = grower.update(String.format(InsertNotify, suffix), idUser, v);
        } else {
            updated = grower.update(String.format(UpdateNotify, suffix), v, idUser);
        }

        return updated;
    }

    public static long getNotify(ForestGrower grower, String suffix, int idUser)
            throws SQLException {
        Long value = grower.selectOne(
                String.format(ExsitsNotify, suffix),
                new LongTransducer(),
                idUser);
        return value == null ? 0 : value.longValue();

    }

    private static long sy(int index, long v, int value) {
        if (value == 1) {
            v |= 1 << index;
        } else {
            v &= ~(1 << index);
        }
        return v;
    }

    public static void main(String[] args) {
        long s = 0;

        System.out.println(Long.toString(s = sy(23, s, 1), 2));
        System.out.println(Long.toString(s = sy(3, s, 1), 2));
        System.out.println(Long.toString(s = sy(3, s, 0), 2));
        System.out.println(Long.toString(s = sy(4, s, 1), 2));
        System.out.println(Long.toString(s = sy(5, s, 0), 2));

    }
}
