//package rank;
//
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//
//import org.apache.commons.pool.impl.GenericObjectPool;
//
//import fengfei.shard.Ploy;
//import fengfei.shard.impl.RandomPloy;
//import fengfei.shard.redis.RedisComand;
//import fengfei.shard.redis.ShardsRedis;
//
//public class RedisPoolsMain {
//
//    final static String RankPopularKey = "RankPopular";
//    final static double MaxScore = 100D;
//
//    public static void main(String[] args) {
//        String hosts = "172.17.20.21:6379 172.17.20.21:6380 172.17.20.21:6381 ";
//        GenericObjectPool.Config config = new GenericObjectPool.Config();
//        Ploy ploy = new RandomPloy();
//        ShardsRedis redis = new ShardsRedis(hosts, 5000, ploy, config);
//
//        RedisComand cmd = redis.createRedisCommand();
//        AtomicLong next = new AtomicLong();
//        Random random = new Random(1829);
//        long start = System.currentTimeMillis();
//        while (true) {
//
//            long i = next.getAndIncrement();
//            int id = random.nextInt();
//            incr(cmd, String.valueOf(id), 1);
//
//            if (i % 10000 == 0) {
//                long end = System.currentTimeMillis();
//                System.out.printf("%d times: %d \n", i, end - start);
//                start = System.currentTimeMillis();
//            }
//        }
//
//    }
//
//    public static double incr(RedisComand cmd, String id, int percent) {
//        percent = percent > 50 ? 50 : percent;
//        Double d = cmd.zscore(RankPopularKey, id);
//        double incr = (MaxScore - (d == null ? 0d : d.doubleValue())) * percent / 100;
//        Double score = cmd.zincrby(RankPopularKey, (incr < 0 ? 0d : incr), id);
//        return score;
//    }
//
//}
