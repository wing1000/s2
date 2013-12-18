package fengfei.ucm.service.relation;

import redis.clients.util.SafeEncoder;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Date: 13-11-11
 * @Time: 下午5:53
 */
public class KeyGenerator {
    final static Map<Byte, String> TypeToAttachedPrefix = new ConcurrentHashMap<>();

    static {
        TypeToAttachedPrefix.put((byte) 1, "M");
    }

    public static void putAttachedPrefix(byte type, String attachedPrefix) {
        TypeToAttachedPrefix.put(type, attachedPrefix);
    }

    public static String getAttachedPrefix(byte type) {
        String c = TypeToAttachedPrefix.get(type);
        if (c == null) {
            throw new NoSuchElementException(
                    String.format("no config attached prefix for type: %d ( %s )",
                                  type,
                                  (char) type));
        }
        return c;
    }

    final static String Following = ">";
    final static String Followed = "<";

    public static String genFollowing(long sourceId, byte type) {
        return new StringBuffer().append(sourceId).append(Following).append(Integer.toHexString(
                type)).toString();
    }

    public static String genFollowed(long targetId, byte type) {
        return new StringBuffer().append(targetId).append(Followed).append(Integer.toHexString(type)).toString();
    }

    public static byte[] genFollowingKey(long sourceId, byte type) {
        return SafeEncoder.encode(genFollowing(sourceId, type));
    }

    public static byte[] genFollowedKey(long targetId, byte type) {
        return SafeEncoder.encode(genFollowed(targetId, type));
    }

    //attachment
    public static String genAttachedFollowing(String attachmentPrefix, long sourceId, byte type) {
        return new StringBuffer().append(attachmentPrefix).append(sourceId).append(Following).append(Integer.toHexString(
                type)).toString();
    }

    public static String genAttachedFollowed(String attachmentPrefix, long targetId, byte type) {
        return new StringBuffer().append(attachmentPrefix).append(targetId).append(Followed).append(Integer.toHexString(
                type)).toString();
    }

    public static byte[] genAttachedFollowingKey(String attachmentPrefix, long sourceId, byte type) {
        return SafeEncoder.encode(genAttachedFollowing(attachmentPrefix, sourceId, type));
    }

    public static byte[] genAttachedFollowedKey(String attachmentPrefix, long targetId, byte type) {
        return SafeEncoder.encode(genAttachedFollowed(attachmentPrefix, targetId, type));
    }
}
