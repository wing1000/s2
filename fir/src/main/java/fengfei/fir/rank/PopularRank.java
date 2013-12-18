package fengfei.fir.rank;

import fengfei.shard.redis.RedisCommand;
import fengfei.sprucy.AppConstants;
import redis.clients.jedis.Tuple;

import java.util.Set;

public class PopularRank {

    final static String RankPopularKey = "RankPopular";
    private String rankPopularKey = RankPopularKey;

    public PopularRank(String rankPopularKey) {
        super();
        this.rankPopularKey = rankPopularKey;
    }

    public PopularRank() {

    }

    public static RedisCommand read;
    public static RedisCommand write;

    public long add(String id, double score) {
        long updated = write.zadd(rankPopularKey, score, id);
        return updated;
    }

    public long add(long id, double score) {
        long updated = add(String.valueOf(id), score);
        return updated;
    }

    public long delete(String id) {
        long updated = write.zrem(rankPopularKey, id);
        return updated;
    }

    public long delete(long id) {
        long updated = delete(String.valueOf(id));
        return updated;
    }

    public Set<Tuple> findWithScore(int offset, int limit) {
        Set<Tuple> tuples = read.zrevrangeByScoreWithScores(
            rankPopularKey,
            100D,
            AppConstants.PopularMinScore,
            offset,
            limit);

        return tuples;

    }

    public Set<String> find(int offset, int limit) {
        Set<String> values = read.zrevrangeByScore(
            rankPopularKey,
            100D,
            AppConstants.PopularMinScore,
            offset,
            limit);

        return values;

    }

    public long count() {
        return read.zcount(rankPopularKey, AppConstants.PopularMinScore, 100d);
    }

    public long removeSinking() {
        long size = write.zremrangeByScore(rankPopularKey, 0, AppConstants.PopularMinScore - 1);
        return size;
    }

}
