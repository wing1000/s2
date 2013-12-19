package fengfei.web.app.init;

import fengfei.fir.rank.LastRank;
import fengfei.fir.rank.PopularRank;
import fengfei.fir.rank.UpcomingRank;
import fengfei.forest.slice.plotter.HashPlotter;
import fengfei.shard.Ploy;
import fengfei.shard.Selector;
import fengfei.shard.redis.JedisShards;
import fengfei.shard.redis.RedisCommand;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class RedisPlugin extends BaseInit {

    static Logger logger = LoggerFactory.getLogger(RedisPlugin.class);
    final static String HostsKey = "redis.hosts";
    final static String TimeoutKey = "redis.timeout";
    final static String PloyKey = "redis.ploy.class";

    public void onApplicationStart() {
        Properties  p = init();
        String hosts = p.getProperty(HostsKey, "localhost:6379");
        String timeoutStr = p.getProperty(TimeoutKey, "localhost:6379");
        int timeout = Integer.parseInt(timeoutStr);
        String ployClass = p.getProperty(PloyKey, HashPlotter.class.getCanonicalName());
        try {
            Ploy ploy = (Ploy) Class.forName(ployClass).newInstance();
            logger.info("init redis shards.");
            JedisShards redis = new JedisShards(hosts, timeout, ploy, readConfig());

            //
            LastRank.read = redis.create(RedisCommand.class);
            LastRank.write = redis.create(RedisCommand.class, Selector.Write);
            //
            //
            PopularRank.read = redis.create(RedisCommand.class);
            PopularRank.write = redis.create(RedisCommand.class, Selector.Write);
            //
            UpcomingRank.read = redis.create(RedisCommand.class);
            UpcomingRank.write = redis.create(RedisCommand.class, Selector.Write);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Config readConfig() {
        String prefix = "redis.pool.";
        Config cfg = new Config();
        Properties p = init();
        cfg.lifo = Boolean.valueOf(p.getProperty(prefix + "lifo", "true"));
        cfg.maxActive = Integer.valueOf(p.getProperty(prefix + "maxActive", "18"));
        cfg.maxIdle = Integer.valueOf(p.getProperty(prefix + "maxIdle", "6"));
        cfg.maxWait = Integer.valueOf(p.getProperty(prefix + "maxWait", "150000"));
        cfg.minEvictableIdleTimeMillis = Integer.valueOf(p.getProperty(prefix
                                                                               + "minEvictableIdleTimeMillis",
                                                                       "100000"));
        cfg.minIdle = Integer.valueOf(p.getProperty(prefix + "minIdle", "0"));
        cfg.numTestsPerEvictionRun = Integer.valueOf(p.getProperty(prefix
                                                                           + "numTestsPerEvictionRun", "1"));
        cfg.testOnBorrow = Boolean.valueOf(p.getProperty(prefix + "testOnBorrow", "false"));
        cfg.testOnReturn = Boolean.valueOf(p.getProperty(prefix + "testOnReturn", "false"));
        cfg.testWhileIdle = Boolean.valueOf(p.getProperty(prefix + "testWhileIdle", "false"));
        cfg.timeBetweenEvictionRunsMillis = Integer.valueOf(p.getProperty(prefix
                                                                                  + "timeBetweenEvictionRunsMillis",
                                                                          "120000"));
        cfg.whenExhaustedAction = Byte.valueOf(
                p.getProperty(prefix + "whenExhaustedAction", "1"),
                10);

        return cfg;

    }
}
