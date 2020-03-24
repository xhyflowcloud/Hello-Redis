package lovenn.net.pool;

import redis.clients.jedis.Jedis;

import java.util.Date;

public class RedisPool extends Pool<XRedis> {
    private String host;

    private Integer port;

    private String password;

    @Override
    protected XRedis allocate() {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        return new XRedis(jedis, new Date().toString());
    }
}
