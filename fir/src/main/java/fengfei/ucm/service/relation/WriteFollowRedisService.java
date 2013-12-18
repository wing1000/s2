package fengfei.ucm.service.relation;

import fengfei.shard.redis.RedisCommand;
import fengfei.ucm.service.WriteFollowService;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.List;

import static fengfei.ucm.service.relation.KeyGenerator.*;


public class WriteFollowRedisService implements WriteFollowService {


    private RedisCommand writer;

    public WriteFollowRedisService(RedisCommand writer) {
        this.writer = writer;
    }

    private interface Callback<T> {
        T execute(Transaction transaction);

    }

    private <T> T execute(Callback<T> callback) {
        Transaction transaction = null;
        try {
            transaction = writer.multi();
            T t = callback.execute(transaction);
            transaction.exec();
            return t;
        } catch (Throwable e) {
            transaction.discard();
            throw e;
        }

    }

    @Override
    public boolean add(
            ArrayList<Object> results,
            final long sourceId,
            final long targetId,
            final byte type) throws Exception {
        Boolean updated = execute(new Callback<Boolean>() {
            @Override
            public Boolean execute(Transaction transaction) {
                String fkey = genFollowing(sourceId, type);
                String fvalue = String.valueOf(targetId);
                transaction.sadd(fkey, fvalue);
                //
                String bkey = genFollowed(targetId, type);
                String bvalue = String.valueOf(sourceId);
                transaction.sadd(bkey, bvalue);
                return true;
            }
        });

        return updated == null ? false : updated;
    }

    @Override
    public boolean remove(
            ArrayList<Object> results,
            final long sourceId,
            final long targetId,
            final byte type) throws Exception {

        Boolean updated = execute(new Callback<Boolean>() {
            @Override
            public Boolean execute(Transaction transaction) {
                String fkey = genFollowing(sourceId, type);
                String fvalue = String.valueOf(targetId);
                transaction.srem(fkey, fvalue);
                //
                String bkey = genFollowed(targetId, type);
                String bvalue = String.valueOf(sourceId);
                transaction.srem(bkey, bvalue);
                return true;
            }
        });

        return updated == null ? false : updated;
    }

    @Override
    public boolean add(
            ArrayList<Object> results,
            final long sourceId,
            final List<Long> targetIds,
            final byte type) throws Exception {

        Boolean updated = execute(new Callback<Boolean>() {
            @Override
            public Boolean execute(Transaction transaction) {
                String fkey = genFollowing(sourceId, type);
                for (int i = 0; i < targetIds.size(); i++) {
                    long targetId = targetIds.get(i);
                    String fvalue = String.valueOf(targetId);
                    transaction.sadd(fkey, fvalue);
                }
                //
                String bvalue = String.valueOf(sourceId);
                for (int i = 0; i < targetIds.size(); i++) {
                    long targetId = targetIds.get(i);
                    String bkey = genFollowed(targetId, type);
                    transaction.sadd(bkey, bvalue);
                }

                return true;
            }
        });

        return updated == null ? false : updated;
    }

    @Override
    public boolean remove(
            ArrayList<Object> results,
            final long sourceId,
            final List<Long> targetIds,
            final byte type) throws Exception {


        Boolean updated = execute(new Callback<Boolean>() {
            @Override
            public Boolean execute(Transaction transaction) {

                String fkey = genFollowing(sourceId, type);
                for (int i = 0; i < targetIds.size(); i++) {
                    long targetId = targetIds.get(i);
                    String fvalue = String.valueOf(targetId);
                    transaction.srem(fkey, fvalue);
                }

                //
                String bvalue = String.valueOf(sourceId);
                for (int i = 0; i < targetIds.size(); i++) {
                    long targetId = targetIds.get(i);
                    String bkey = genFollowed(targetId, type);
                    transaction.srem(bkey, bvalue);
                }

                return true;
            }
        });

        return updated == null ? false : updated;
    }

    //attachment
    @Override
    public boolean add(
            ArrayList<Object> results,
            final long sourceId,
            final long targetId,
            final byte type,
            final long attachmentId) throws Exception {

        Boolean updated = execute(new Callback<Boolean>() {
            @Override
            public Boolean execute(Transaction transaction) {

                //add relation
                String fkey = genFollowing(sourceId, type);
                String fvalue = String.valueOf(targetId);
                transaction.sadd(fkey, fvalue);
                //
                String bkey = genFollowed(targetId, type);
                String bvalue = String.valueOf(sourceId);
                transaction.sadd(bkey, bvalue);

                //add message attachment
                String attachmentValue = String.valueOf(attachmentId);
                String attachedPrefix = getAttachedPrefix(type);
                String fmkey = genAttachedFollowing(attachedPrefix, sourceId, type);
                transaction.sadd(fmkey, attachmentValue);
                //
                String bmkey = genAttachedFollowed(attachedPrefix, targetId, type);
                transaction.sadd(bmkey, attachmentValue);
                return true;
            }
        });

        return updated == null ? false : updated;
    }

    @Override
    public boolean remove(
            ArrayList<Object> results,
            final long sourceId,
            final long targetId,
            final byte type,
            final long attachmentId) throws Exception {

        Boolean updated = execute(new Callback<Boolean>() {
            @Override
            public Boolean execute(Transaction transaction) {
                String fkey = genFollowing(sourceId, type);
                String fvalue = String.valueOf(targetId);
                transaction.srem(fkey, fvalue);
                //
                String bkey = genFollowed(targetId, type);
                String bvalue = String.valueOf(sourceId);
                transaction.srem(bkey, bvalue);
                //add message attachment
                String attachmentValue = String.valueOf(attachmentId);
                String attachedPrefix = getAttachedPrefix(type);
                String fakey = genAttachedFollowing(attachedPrefix, sourceId, type);
                transaction.srem(fakey, attachmentValue);
                //
                String bakey = genAttachedFollowed(attachedPrefix, targetId, type);
                transaction.srem(bakey, attachmentValue);


                return true;
            }
        });

        return updated == null ? false : updated;
    }

    @Override
    public boolean add(
            ArrayList<Object> results,
            final long sourceId,
            final List<Long> targetIds,
            final byte type,
            final long attachmentId) throws Exception {


        Boolean updated = execute(new Callback<Boolean>() {
            @Override
            public Boolean execute(Transaction transaction) {
                String fkey = genFollowing(sourceId, type);
                for (int i = 0; i < targetIds.size(); i++) {
                    long targetId = targetIds.get(i);
                    String fvalue = String.valueOf(targetId);
                    transaction.sadd(fkey, fvalue);
                }
                //
                String bvalue = String.valueOf(sourceId);
                for (int i = 0; i < targetIds.size(); i++) {
                    long targetId = targetIds.get(i);
                    String bkey = genFollowed(targetId, type);
                    transaction.sadd(bkey, bvalue);
                }

                //attachment
                String attachmentValue = String.valueOf(attachmentId);
                String attachedPrefix = getAttachedPrefix(type);
                String fakey = genAttachedFollowing(attachedPrefix, sourceId, type);
                transaction.sadd(fakey, attachmentValue);

                //

                for (int i = 0; i < targetIds.size(); i++) {
                    long targetId = targetIds.get(i);
                    String bakey = genAttachedFollowed(attachedPrefix, targetId, type);
                    transaction.sadd(bakey, attachmentValue);
                }


                return true;
            }
        });

        return updated == null ? false : updated;
    }

    @Override
    public boolean remove(
            ArrayList<Object> results,
            final long sourceId,
            final List<Long> targetIds,
            final byte type,
            final long attachmentId) throws Exception {


        Boolean updated = execute(new Callback<Boolean>() {
            @Override
            public Boolean execute(Transaction transaction) {
                String fkey = genFollowing(sourceId, type);
                for (int i = 0; i < targetIds.size(); i++) {
                    long targetId = targetIds.get(i);
                    String fvalue = String.valueOf(targetId);
                    transaction.srem(fkey, fvalue);
                }

                //
                String bvalue = String.valueOf(sourceId);
                for (int i = 0; i < targetIds.size(); i++) {
                    long targetId = targetIds.get(i);
                    String bkey = genFollowed(targetId, type);
                    transaction.srem(bkey, bvalue);
                }

                //attachment
                String attachmentValue = String.valueOf(attachmentId);
                String attachedPrefix = getAttachedPrefix(type);
                String fakey = genAttachedFollowing(attachedPrefix, sourceId, type);
                transaction.srem(fakey, attachmentValue);

                //

                for (int i = 0; i < targetIds.size(); i++) {
                    long targetId = targetIds.get(i);
                    String bakey = genAttachedFollowed(attachedPrefix, targetId, type);
                    transaction.srem(bakey, attachmentValue);
                }

                return true;
            }
        });

        return updated == null ? false : updated;
    }


}
