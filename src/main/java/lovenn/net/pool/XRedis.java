package lovenn.net.pool;

import redis.clients.jedis.Jedis;

public class XRedis{
    private final Jedis jedis;
    private final String uuid;

    public XRedis(Jedis jedis, String uuid) {
        this.jedis = jedis;
        this.uuid = uuid;
    }

    public Jedis getJedis() {
        return jedis;
    }

    public String getUuid() {
        return uuid;
    }
}
