package fengfei.fir.utils;

import com.google.common.net.InetAddresses;
import fengfei.ucm.entity.profile.User;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class AppUtils {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
    public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyy-MM-dd HH24:ss");

    public static String toNiceName(User user) {
        if (user.niceName == null || "".equals(user.niceName)) {
            user.niceName = user.realName;
        }
        if (user.niceName == null || "".equals(user.niceName)) {
            user.niceName = user.userName;
        }
        return user.niceName;
    }

    public static String toLike(String str) {
        return "%" + str + "%";
    }

    public static String toLikes(String field, String... str) {
        StringBuilder sb = new StringBuilder();
        for (String s : str) {
            if (sb.length() > 0) {
                sb.append(" and ");
            }
            sb.append(field).append(" like ").append("%").append(s).append("%");

        }
        return sb.toString();
    }

    public static String toEquals(String field, String... str) {
        StringBuilder sb = new StringBuilder();
        for (String s : str) {
            if (sb.length() > 0) {
                sb.append(" and ");
            }
            sb.append(field).append(" = ").append("'").append(s).append("'");

        }
        return sb.toString();
    }

    public static String toInCause(Collection<? extends Number> numbers) {
        StringBuilder sb = new StringBuilder();
        for (Number long1 : numbers) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(long1);

        }
        return sb.toString();

    }

    public static String int2ip(int i) {
        Inet4Address address = InetAddresses.fromInteger(i);
        String addressStr = InetAddresses.toAddrString(address);
        return addressStr;

    }

    public static int ip2int(String ip) {
        if (ip == null || "".equals(ip)) {
            return 0;
        }
        InetAddress addr = InetAddresses.forString(ip);
        // Convert to int
        int address = InetAddresses.coerceToInteger(addr);
        return address;

    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static long bytes2long(byte[] b) {
        long temp = 0;
        long res = 0;
        for (int i = 0; i < 8; i++) {
            res <<= 8;
            temp = b[i] & 0xff;
            res |= temp;
        }
        return res;
    }

    public static byte[] long2bytes(long num) {
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) (num >>> (56 - (i * 8)));
        }
        return b;
    }

    public static byte[] int2bytes(int i) {
        byte[] b = new byte[4];

        b[0] = (byte) (0xff & i);
        b[1] = (byte) ((0xff00 & i) >> 8);
        b[2] = (byte) ((0xff0000 & i) >> 16);
        b[3] = (byte) ((0xff000000 & i) >> 24);
        return b;
    }

    public static int bytes2int(byte[] bytes) {
        int num = bytes[0] & 0xFF;
        num |= ((bytes[1] << 8) & 0xFF00);
        num |= ((bytes[2] << 16) & 0xFF0000);
        num |= ((bytes[3] << 24) & 0xFF000000);
        return num;
    }

}
