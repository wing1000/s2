package fengfei.fir.utils;

import org.apache.commons.codec.binary.Base64;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.*;

/**
 * 
 基于网络Mac地址的UUID
 */
public class UUID {

    public static void main(String[] args) {
        Base64 base64 = new Base64();
        byte[] bs = base64.encode(UUID.randomB36UUID().getBytes());
        System.out.println(new String(bs));
        System.out.println(UUID.randomB32UUID());
        System.out.println(UUID.randomB36UUID());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toB32String());
        System.out.println(UUID.randomUUID().toB24String());
        System.out.println(UUID.randomUUID().toB28String());
        System.out.println(java.util.UUID.randomUUID().toString());
        int num = 1000000;
        Set<String> sets = new HashSet<>();

        for (int i = 0; i < num; i++) {
            sets.add(UUID.randomUUID().toB24String());
            if (i % 100000 == 0) {
                System.out.println(i);
            }
        }
        System.out.printf("%d %d", sets.size(), num);

    }

    /**
     * Explicit serialVersionUID for interoperability.
     */
    private static final long serialVersionUID = -4856846361193249489L;
    /*
     * The most significant 64 bits of this UUID.
     * 
     * @serial
     */
    private final long mostSigBits;
    /*
     * The least significant 64 bits of this UUID.
     * 
     * @serial
     */
    private final long leastSigBits;
    /*
     * The random number generator used by this class to create random based UUIDs.
     */
    private static volatile SecureRandom numberGenerator = null;
    /** 所有物理mac地址的byte码 */
    private static byte[] macBits = null;
    static {
        macBits = getAllHardwareAddressByte();
    }

    private UUID(byte[] data) {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16;
        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    public static String randomB36UUID() {
        return randomUUID().toString();
    }

    public static String randomB32UUID() {
        return randomUUID().toB32String();
    }

    public static String randomB36UUID(byte[] seed) {
        return randomUUID(seed).toString();
    }

    public static String randomB32UUID(byte[] seed) {
        return randomUUID(seed).toB32String();
    }

    public static String randomB28UUID() {
        return randomUUID().toB28String();
    }

    public static String randomB24UUID(byte[] seed) {
        return randomUUID(seed).toB24String();
    }

    public static String randomB24UUID() {
        return randomUUID().toB24String();
    }

    public static String randomB28UUID(byte[] seed) {
        return randomUUID(seed).toB28String();
    }

    public static UUID randomUUID() {
        return randomUUID(macBits);
    }

    public static UUID randomUUID(byte[] seed) {
        SecureRandom ng = numberGenerator;
        if (ng == null) {
            if (seed == null || seed.length == 0) {
                numberGenerator = ng = new SecureRandom();
            } else {
                numberGenerator = ng = new SecureRandom(seed);
            }
        }
        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f; /* clear version */
        randomBytes[6] |= 0x40; /* set to version 4 */
        randomBytes[8] &= 0x3f; /* clear variant */
        randomBytes[8] |= 0x80; /* set to IETF variant */
        return new UUID(randomBytes);
    }

    /**
     * Returns a <code>String</code> object representing this <code>UUID</code>.
     * 
     * <p>
     * The UUID string representation is as described by this BNF : <blockquote>
     * 
     * <pre>
     * {@code
     * UUID                   = <time_low> "-" <time_mid> "-"
     *                          <time_high_and_version> "-"
     *                          <variant_and_sequence> "-"
     *                          <node>
     * time_low               = 4*<hexOctet>
     * time_mid               = 2*<hexOctet>
     * time_high_and_version  = 2*<hexOctet>
     * variant_and_sequence   = 2*<hexOctet>
     * node                   = 6*<hexOctet>
     * hexOctet               = <hexDigit><hexDigit>
     * hexDigit               =
     *       "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *       | "a" | "b" | "c" | "d" | "e" | "f"
     *       | "A" | "B" | "C" | "D" | "E" | "F"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @return a string representation of this <tt>UUID</tt>.
     */
    public String toString() {
        return (digits(mostSigBits >> 32, 8) + "-" + digits(mostSigBits >> 16, 4) + "-"
                + digits(mostSigBits, 4) + "-" + digits(leastSigBits >> 48, 4) + "-" + digits(
            leastSigBits,
            12));
    }

    public String toB32String() {
        return (digits(mostSigBits >> 32, 8) + digits(mostSigBits >> 16, 4)
                + digits(mostSigBits, 4) + digits(leastSigBits >> 48, 4) + digits(leastSigBits, 12));
    }

    public String toB28String() {
        return (digits32(mostSigBits >> 32, 8) + "-" + digits32(mostSigBits >> 16, 4) + "-"
                + digits32(mostSigBits, 4) + "-" + digits32(leastSigBits >> 48, 4) + "-" + digits32(
            leastSigBits,
            12));
    }

    public String toB24String() {
        return (digits32(mostSigBits >> 32, 8) + digits32(mostSigBits >> 16, 4)
                + digits32(mostSigBits, 4) + digits32(leastSigBits >> 48, 4) + digits32(
            leastSigBits,
            12));
    }

    private static String digits32(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toString(hi | (val & (hi - 1)), 32).substring(1);
    }

    /** Returns val represented by the specified number of hex digits. */
    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    private static List<byte[]> getHardwareAddress() {
        List<byte[]> macs = new ArrayList<byte[]>();
        try {
            Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
            while (el.hasMoreElements()) {
                NetworkInterface ni = el.nextElement();
                byte[] macb = ni.getHardwareAddress();
                if (macb == null)
                    continue;
                macs.add(macb);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return macs;
    }

    private static byte[] getAllHardwareAddressByte() {
        List<byte[]> macs = new ArrayList<byte[]>();
        int length = 0;
        byte[] bs = null;
        try {
            Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
            while (el.hasMoreElements()) {
                NetworkInterface ni = el.nextElement();
                byte[] macb = ni.getHardwareAddress();
                if (macb == null)
                    continue;
                macs.add(macb);
                length += macb.length;
            }
            bs = new byte[length];
            int pos = 0;
            for (byte[] mb : macs) {
                System.arraycopy(mb, 0, bs, pos, mb.length);
                pos += mb.length;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bs;
    }
}
