package lovenn.net.service.impl;

import lovenn.net.domian.Login;
import lovenn.net.service.LoginService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Resource
    private Jedis jedisService;

    @Override
    public String checkLoginStatus(Login login) {
        if (login == null) return null;
        return jedisService.get("article:login:" + login.getToken());
    }

    @Override
    public void updateLoginStatus(Login login) {
        if (login == null) return;
        jedisService.setex("article:login:" + login.getToken(), 60 * 5, login.getUserId());
        jedisService.zadd("article:recent:", login.getLastTime(), login.getToken());
        if (login.getItems() != null && !login.getItems().isEmpty()) {
            for (String id : login.getItems()) {
                jedisService.zadd("article:viewed:" + login.getToken(), login.getLastTime(), String.valueOf(id));
                jedisService.zremrangeByRank("article:viewed:" + login.getToken(), 0, -26);
            }
        }
    }
}
