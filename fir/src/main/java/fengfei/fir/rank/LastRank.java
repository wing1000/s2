package fengfei.fir.rank;

import fengfei.fir.model.PhotoRank;
import fengfei.fir.utils.Kryor;
import fengfei.shard.redis.RedisCommand;
import fengfei.ucm.entity.photo.Rank;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * 最新添加来排序
 * 
 * @author tietang
 * 
 */
public class LastRank {

    final static String RankLastKey = "RankLast";
    private String rankLastKey = RankLastKey;
    private String rankLastHashKey = rankLastKey + "_H";
    final static int T2013Minute;
    final static int WeekMinute = 7 * 24 * 60;
    public static RedisCommand read;
    public static RedisCommand write;

    static {
        Calendar c = Calendar.getInstance();
        c.set(2013, 0, 1, 0, 0, 0);
        T2013Minute = (int) (c.getTimeInMillis() / 1000 / 60);// 从2013开始为0
    }

    public LastRank(String rankLastKey) {
        this.rankLastKey = rankLastKey;
        rankLastHashKey = rankLastKey + "_H";
    }

    public LastRank() {
    }

    public long add(Rank rank) {
        long time = (rank.updateAt / 1000 / 60) - T2013Minute;
        Transaction ta = write.multi();
        String id = String.valueOf(rank.idPhoto);
        ta.zadd(rankLastKey, time, id);

        byte[] bs = Kryor.write(PhotoRank.createPhotoRank(rank));
        // ta.hset(rankLastHashKey, id, rank);
        List<Object> rs = ta.exec();
        return 1;

    }

    public long add(String id, long seconds) {
        long time = (seconds / 60) - T2013Minute;
        return write.zadd(rankLastKey, time, id);
    }

    public long add(long id, long seconds) {
        return add(String.valueOf(id), seconds);
    }

    public long delete(long id) {
        return delete(String.valueOf(id));
    }

    public long delete(String id) {
        return write.zrem(rankLastKey, id);
    }

    public long count() {
        long[] r = range();
        return read.zcount(rankLastKey, r[0], r[1]);
    }

    private long[] range() {
        long max = 1 + System.currentTimeMillis() / 1000 / 60 - T2013Minute;
        long min = max - WeekMinute;
        System.out.println(min + " " + max);
        return new long[] { min, max };
    }

    public Set<String> find(int offset, int limit) {
        long[] r = range();
       
        return read.zrangeByScore(rankLastKey, r[0], r[1], offset, limit);
    }

    public Set<Tuple> findWithScore(int offset, int limit) {
        long[] r = range();
        return read.zrangeByScoreWithScores(rankLastKey, r[0], r[1], offset, limit);
    }

    public long removeExpired() {
        long last = ((System.currentTimeMillis() / 1000 / 60)) - T2013Minute - WeekMinute;
        long size = write.zremrangeByScore(rankLastKey, 0, last);
        return size;
    }
}
