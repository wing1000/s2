package fengfei.fir.rank;

import fengfei.shard.redis.RedisCommand;
import redis.clients.jedis.Transaction;
import redis.clients.util.SafeEncoder;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * 最新添加来排序
 *
 * @author tietang
 */
public class TagsQuery {

    final static String MainKey = "S_TAGS";
    private String key = MainKey;
    final static int T2013Minute;
    final static int WeekMinute = 7 * 24 * 60;
    public static RedisCommand read;
    public static RedisCommand write;

    static {
        Calendar c = Calendar.getInstance();
        c.set(2013, 0, 1, 0, 0, 0);
        T2013Minute = (int) (c.getTimeInMillis() / 1000 / 60);// 从2013开始为0
    }

    public TagsQuery(String key) {
        this.key = key;
    }

    public TagsQuery() {
    }

    public long add(long id, String... tagNames) {
        Transaction ta = write.multi();
        for (String tagName : tagNames) {
            ta.sadd(tagName, String.valueOf(id));
        }
        List<Object> rs = ta.exec();
        return rs.size();
    }

    public long delete(long id, String... tagNames) {
        Transaction ta = write.multi();
        for (String tagName : tagNames) {

            ta.srem(tagName, String.valueOf(id));
        }
        List<Object> rs = ta.exec();
        return rs.size();
    }

    public long delete(String tagName) {
        return write.del(SafeEncoder.encode(tagName));
    }

    public long count(String tagName) {
        return read.scard(tagName);
    }


    public Set<String> find(String tagName, int offset, int limit) {
        Set<String> members = read.smembers(tagName);

        return members;
    }

}
