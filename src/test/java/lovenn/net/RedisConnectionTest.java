package lovenn.net;

import lovenn.net.domian.Article;
import lovenn.net.domian.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.util.Map;
import java.util.Set;

public class RedisConnectionTest {
    public static void main(String[] args) {

        JedisShardInfo jedisShardInfo = new JedisShardInfo("140.143.222.13", 6379);
        jedisShardInfo.setPassword("foobared");
        Jedis jedis = new Jedis(jedisShardInfo);

        //jedis.lpush("mylist", "hello", "redis");
//        System.out.println(jedis.keys("*"));
//        System.out.println(jedis.lrange("mylist", 0, -1));
        User user = new User();
        user.setName("sunyanan");
        user.setUserId("10000000");
        Set<String> ids = jedis.zrevrange("article:time", 0, -1);
        for (String id : ids) {
            Map<String, String> map = jedis.hgetAll(id);
            Article article = new Article();
            article.setId(map.get("id"));
            article.setTime(map.get("time"));
            article.setContent(map.get("content"));
            article.setPoster(map.get("poster"));
            article.setTitle(map.get("title"));
            article.setVotes(map.get("votes"));
            article.setVoted(!jedis.sismember("voted:" + id, user.getUserId()));
            System.out.println(article);
        }
    }
}
