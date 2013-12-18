package fengfei.fir.utils;

/**
 * @User: tietang
 */
public class ByteUtils {
    public static long setBit1(long value, int position) {
        value |= 1 << (position - 1);
        return value;
    }

    public static long setBit0(long value, int position) {
        value &= ~(1 << (position - 1));
        return value;
    }


    public static int setBit1(int value, int position) {
        value |= 1 << (position - 1);
        return value;
    }


    public static int setBit0(int value, int position) {
        value &= ~(1 << (position - 1));
        return value;
    }


    public static void main(String[] args) {
        long v = 0;
        v = setBit1(v, 6);
        v = setBit1(v, 1);
        v = setBit0(v, 6);
        System.out.println(192073);
        System.out.println(Long.toBinaryString(v));
    }


}
