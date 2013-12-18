package fengfei.ucm.service.relation;

import fengfei.shard.redis.RedisCommand;
import fengfei.ucm.entity.relation.State;
import fengfei.ucm.service.ReadFollowService;
import redis.clients.util.SafeEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static fengfei.ucm.service.relation.KeyGenerator.*;

public class ReadFollowRedisService implements ReadFollowService {
    private RedisCommand reader;

    public ReadFollowRedisService(RedisCommand reader) {
        this.reader = reader;
    }

    @Override
    public List<Long> findTargets(ArrayList<Object> results, long sourceId, byte type) throws Exception {
        byte[] key = KeyGenerator.genFollowingKey(sourceId, type);
        Set<byte[]> members = reader.smembers(key);
        if (members == null) return null;
        List<Long> targets = new ArrayList<>();

        for (byte[] member : members) {
            String id = SafeEncoder.encode(member);
            targets.add(Long.parseLong(id));
        }
        return targets;
    }

    @Override
    public List<Long> findTargets(
            ArrayList<Object> results,
            long sourceId,
            byte type,
            int offset,
            int limit) throws Exception {
        byte[] key = KeyGenerator.genFollowingKey(sourceId, type);
        Set<byte[]> members = reader.smembers(key);
        if (members == null) return null;
        List<Long> targets = new ArrayList<>();

        for (byte[] member : members) {
            String id = SafeEncoder.encode(member);
            targets.add(Long.parseLong(id));
        }
        return targets;
    }

    @Override
    public int computeTargetCount(ArrayList<Object> results, long sourceId, byte type) throws Exception {
        byte[] key = KeyGenerator.genFollowingKey(sourceId, type);
        long count = reader.scard(key);
        return (int) count;
    }

    @Override
    public int countTarget(ArrayList<Object> results, long sourceId, byte type) throws Exception {
        byte[] key = KeyGenerator.genFollowingKey(sourceId, type);
        long count = reader.scard(key);
        return (int) count;
    }

    @Override
    public List<Long> findSources(ArrayList<Object> results, long targetId, byte type) throws Exception {
        byte[] key = KeyGenerator.genFollowedKey(targetId, type);
        Set<byte[]> members = reader.smembers(key);
        if (members == null) return null;
        List<Long> sources = new ArrayList<>();

        for (byte[] member : members) {
            String id = SafeEncoder.encode(member);
            sources.add(Long.parseLong(id));
        }
        return sources;
    }

    @Override
    public List<Long> findSources(
            ArrayList<Object> results,
            long targetId,
            byte type,
            int offset,
            int limit) throws Exception {
        byte[] key = KeyGenerator.genFollowedKey(targetId, type);
        Set<byte[]> members = reader.smembers(key);
        if (members == null) return null;
        List<Long> sources = new ArrayList<>();

        for (byte[] member : members) {
            String id = SafeEncoder.encode(member);
            sources.add(Long.parseLong(id));
        }
        return sources;
    }

    @Override
    public int computeSourceCount(ArrayList<Object> results, long targetId, byte type) throws Exception {

        byte[] key = KeyGenerator.genFollowedKey(targetId, type);
        long count = reader.scard(key);
        return (int) count;
    }

    @Override
    public int countSource(ArrayList<Object> results, long targetId, byte type) throws Exception {
        byte[] key = KeyGenerator.genFollowedKey(targetId, type);
        long count = reader.scard(key);
        return (int) count;
    }

    @Override
    public int[] count(ArrayList<Object> results, long sourceId, byte type) throws Exception {
        int fcount = countSource(null, sourceId, type);
        int bcount = countTarget(null, sourceId, type);
        return new int[]{fcount, bcount};
    }

    @Override
    public int[] computeCount(ArrayList<Object> results, long sourceId, byte type) throws Exception {
        int fcount = computeSourceCount(null, sourceId, type);
        int bcount = computeTargetCount(null, sourceId, type);
        return new int[]{fcount, bcount};
    }

    @Override
    public boolean isFollow(ArrayList<Object> results, long sourceId, long targetId, byte type) throws Exception {
        byte[] key = KeyGenerator.genFollowingKey(sourceId, type);
        byte[] member = SafeEncoder.encode(String.valueOf(targetId));
        return reader.sismember(key, member);
    }

    //attachment
    @Override
    public List<Long> findTargetAttachments(long sourceId, byte type, State state) throws Exception {
        String attachedPrefix = getAttachedPrefix(type);
        byte[] fakey = genAttachedFollowingKey(attachedPrefix, sourceId, type);
        Set<byte[]> members = reader.smembers(fakey);
        if (members == null) return null;
        List<Long> attachmentIds = new ArrayList<>();

        for (byte[] member : members) {
            String id = SafeEncoder.encode(member);
            attachmentIds.add(Long.parseLong(id));
        }
        return attachmentIds;
    }
    @Override
    public List<Long> findTargetAttachments(
            long sourceId, byte type, State state, int offset, int limit) throws Exception {
        String attachedPrefix = getAttachedPrefix(type);
        byte[] fakey = genAttachedFollowingKey(attachedPrefix, sourceId, type);
        Set<byte[]> members = reader.smembers(fakey);
        if (members == null) return null;
        List<Long> attachmentIds = new ArrayList<>();

        for (byte[] member : members) {
            String id = SafeEncoder.encode(member);
            attachmentIds.add(Long.parseLong(id));
        }
        return attachmentIds;
    }

    @Override
    public List<Long> findSourceAttachments(
            long targetId, byte type, State state, int offset, int limit) throws Exception {
        String attachedPrefix = getAttachedPrefix(type);
        byte[] bakey = genAttachedFollowedKey(attachedPrefix, targetId, type);
        Set<byte[]> members = reader.smembers(bakey);
        if (members == null) return null;
        List<Long> attachmentIds = new ArrayList<>();

        for (byte[] member : members) {
            String id = SafeEncoder.encode(member);
            attachmentIds.add(Long.parseLong(id));
        }
        return attachmentIds;
    }


    @Override
    public List<Long> findSourceAttachments(long targetId, byte type, State state) throws Exception {
        String attachedPrefix = getAttachedPrefix(type);
        byte[] bakey = genAttachedFollowedKey(attachedPrefix, targetId, type);
        Set<byte[]> members = reader.smembers(bakey);
        if (members == null) return null;
        List<Long> attachmentIds = new ArrayList<>();

        for (byte[] member : members) {
            String id = SafeEncoder.encode(member);
            attachmentIds.add(Long.parseLong(id));
        }
        return attachmentIds;
    }
}
