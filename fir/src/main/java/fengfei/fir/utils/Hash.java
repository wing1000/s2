package fengfei.fir.utils;

public class Hash {

    public static final long FNV_BASIS_64 = 0xCBF29CE484222325L;
    public static final long FNV_PRIME_64 = 1099511628211L;

    public static long fnvHash64(long val) {
        long hashval = FNV_BASIS_64;

        for (int i = 0; i < 8; i++) {
            long octet = val & 0x00ff;
            val = val >> 8;

            hashval = hashval ^ octet;
            hashval = hashval * FNV_PRIME_64;
        }
        return Math.abs(hashval);
    }

    public static long murmurHash(String val) {
        return MurmurHashed.hash64(val);
    }

    public static long murmurHash(String val, int seed) {
        return MurmurHashed.hash64A(val.getBytes(), seed);
    }

    public static void main(String[] args) {

    }
}
