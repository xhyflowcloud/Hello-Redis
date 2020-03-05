package lovenn.net;

import redis.clients.jedis.Jedis;

public class RedisConnectionTest {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("140.143.222.13", 6379);

        //jedis.lpush("mylist", "hello", "redis");
        System.out.println(jedis.keys("*"));
        System.out.println(jedis.lrange("mylist", 0, -1));
    }
}
