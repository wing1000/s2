package fengfei.ucm.rank;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Random;

import fengfei.shard.impl.Shards;
import fengfei.shard.redis.JedisShards;
import fengfei.shard.redis.RedisCommand;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fengfei.fir.rank.LastRank;
import fengfei.fir.rank.PopularRank;
import fengfei.shard.Ploy;
import fengfei.shard.Selector;
import fengfei.shard.impl.LoopPloy;
import redis.clients.jedis.Jedis;

public class PopRankTest {

    static JedisShards redis;
    PopularRank rank = new PopularRank("XXX_TEST_POP");

    @BeforeClass
    public static void setUp() throws Exception {
        Config cfg = new Config();
        Ploy ploy = new LoopPloy();

        redis = new JedisShards("127.0.0.1:6379,127.0.0.1:6379", 3000, ploy, cfg);

        //
        PopularRank.read = redis.create(RedisCommand.class);
        ;
        PopularRank.write = redis.create(RedisCommand.class, Selector.Write);
        //
    }

    @AfterClass
    public static void tearDown() throws Exception {
        redis.close();
    }

    @Test
    public void test() {
        Random r = new Random();

        for (int i = 0; i <= 10; i++) {
            double score = Math.abs((double) r.nextInt(10000) / 100);
            // System.out.println(t);
            rank.add(i, score);
        }

        System.out.println(7 * 24 * 60);
        System.out.println(rank.count());
        System.out.println(rank.findWithScore(0, 10));
        System.out.println(rank.findWithScore(0, 2));
        System.out.println(rank.findWithScore(2, 2));

    }
}
