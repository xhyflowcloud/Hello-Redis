package lovenn.net;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

public class RedisConnectionTest {
    public static void main(String[] args) {

        JedisShardInfo jedisShardInfo = new JedisShardInfo("140.143.222.13", 6379);
        jedisShardInfo.setPassword("foobared");
        Jedis jedis = new Jedis(jedisShardInfo);

        //jedis.lpush("mylist", "hello", "redis");
        System.out.println(jedis.keys("*"));
        System.out.println(jedis.lrange("mylist", 0, -1));
    }
}
