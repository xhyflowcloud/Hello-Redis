package lovenn.net.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

@SpringBootConfiguration
public class ServiceConfig {

    @Value("redis.address")
    private String redisAddress;

    @Value("redis.port")
    private Integer port;

    @Bean("jedisService")
    public Jedis jedisService() {
        return new Jedis(redisAddress, port);
    }
}
