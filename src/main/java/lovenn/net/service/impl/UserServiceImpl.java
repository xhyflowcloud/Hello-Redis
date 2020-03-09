package lovenn.net.service.impl;

import lovenn.net.domian.User;
import lovenn.net.service.UserService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final int EXPIRE_5 = 60 * 5;

    @Resource
    private Jedis jedisService;

    @Override
    public User getUser(String name) {
        Map<String, String> userMap = jedisService.hgetAll("user:" + name);
        if(userMap != null) {
            String userId = userMap.get("userId");
            if(userId != null) {
                User user = new User();
                user.setName(name);
                user.setUserId(userId);
                return user;
            }
        }
        return null;
    }

    @Override
    public void login(User user) {
        if(user == null) return;
        Map<String, String> userMap = jedisService.hgetAll("user:" + user.getName());
        if(userMap != null) {
            jedisService.setex("login:" + user.getName(), EXPIRE_5, "stated");
        }
    }

    @Override
    public boolean isLogin(String name) {
        String state = jedisService.get("login:" + name);
        if(state == null) return false;
        return "stated".equals(state);
    }
}
