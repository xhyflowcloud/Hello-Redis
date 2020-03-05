package lovenn.net.service.impl;

import lovenn.net.domian.Article;
import lovenn.net.domian.User;
import lovenn.net.service.ArticleService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    private static final long ONE_WEEK_IN_SECONDS = 7 * 86400;

    private static final int VOTE_SCORE = 432;

    @Resource
    private Jedis jedisService;

    @Override
    public void vote(User user, Article article) {
        jedisService.zscore("time:", article)
    }
}
