package fengfei.fir.rank;

import fengfei.shard.redis.RedisCommand;
import fengfei.sprucy.AppConstants;
import redis.clients.jedis.Tuple;

import java.util.Set;

public class UpcomingRank {

    final static String RankUpcomingKey = "RankUpcoming";
    private String rankUpcomingKey = RankUpcomingKey;

    public static RedisCommand read;
    public static RedisCommand write;

    public UpcomingRank(String rankUpcomingKey) {
        super();
        this.rankUpcomingKey = rankUpcomingKey;
    }

    public UpcomingRank() {

    }

    public long add(String id, double score) {
        long updated = write.zadd(rankUpcomingKey, score, id);
        return updated;
    }

    public long add(long id, double score) {
        long updated = add(String.valueOf(id), score);
        return updated;
    }

    public long delete(String id) {
        long updated = write.zrem(rankUpcomingKey, id);
        return updated;
    }

    public long delete(long id) {
        long updated = delete(String.valueOf(id));
        return updated;
    }

    public Set<Tuple> findWithScore(int offset, int limit) {
        Set<Tuple> tuples = read.zrevrangeByScoreWithScores(
                rankUpcomingKey,
                AppConstants.UpcomingMaxScore,
                AppConstants.UpcomingMinScore,
                offset,
                limit);

        return tuples;

    }

    public Set<String> find(int offset, int limit) {
        Set<String> values = read.zrevrangeByScore(
                rankUpcomingKey,
                AppConstants.UpcomingMaxScore,
                AppConstants.UpcomingMinScore,
                offset,
                limit);

        return values;

    }

    public long count() {
        return read.zcount(rankUpcomingKey, AppConstants.PopularMinScore, 100d);
    }

    public long removeSinking() {
        long size = write.zremrangeByScore(rankUpcomingKey, 0, AppConstants.UpcomingMinScore - 1);
        return size;
    }

    public long removeRise() {
        long size = write.zremrangeByScore(rankUpcomingKey, AppConstants.PopularMinScore, 100D);
        return size;
    }

}
