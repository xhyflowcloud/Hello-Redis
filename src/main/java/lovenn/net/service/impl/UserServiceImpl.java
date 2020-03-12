package lovenn.net.service.impl;

import lovenn.net.domian.User;
import lovenn.net.service.UserService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private Jedis jedisService;

    @Override
    public User getUser(String userId) {
        if(userId == null) return null;
        Map<String, String> userMap = jedisService.hgetAll("article:user:" + userId);
        if (userMap != null) {
            String name = userMap.get("name");
            User user = new User();
            user.setUserId(userId);
            if (name != null) {
                user.setName(name);
            }
            return user;
        }
        return null;
    }
}
