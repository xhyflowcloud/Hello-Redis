package lovenn.net.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

@SpringBootConfiguration
public class ServiceConfig {

//    @Value("redis.address")
    private String redisAddress = "140.143.222.13";

//    @Value("redis.port")
    private String port = "6379";

    @Bean(name = "jedisService", destroyMethod = "")
    public Jedis jedisService() {
        JedisShardInfo jedisShardInfo = new JedisShardInfo("140.143.222.13", 6379);
        jedisShardInfo.setPassword("foobared");
        return new Jedis(jedisShardInfo);
    }


}
