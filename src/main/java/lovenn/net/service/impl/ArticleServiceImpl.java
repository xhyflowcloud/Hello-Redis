package lovenn.net.service.impl;

import lovenn.net.domian.Article;
import lovenn.net.domian.User;
import lovenn.net.service.ArticleService;
import lovenn.net.util.Generator;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    private static final long ONE_WEEK_IN_SECONDS = 7 * 86400;

    private static final int VOTE_SCORE = 432;

    private static final int ARTICLES_PER_PAGE = 25;

    @Resource
    private Jedis jedisService;

    @Resource
    private Generator defaultGenerator;



    @Override
    public void vote(User user, Article article) {

        long cutoff = System.currentTimeMillis()/1000 - ONE_WEEK_IN_SECONDS;
        Double scoreTime = jedisService.zscore("articles:time", article.getId());
        if(scoreTime != null && scoreTime < cutoff) return;
        jedisService.sadd("voted:" + article.getId(), user.getUserId());
        jedisService.zincrby("articles:score", VOTE_SCORE, article.getId());
        jedisService.hincrBy("article:" + article.getId(), "votes", 1);
    }

    @Override
    public void post(User user, Article article) {
        article.setId(String.valueOf(defaultGenerator.generate()));
        //当前秒数
        long now = System.currentTimeMillis()/1000;
        article.setTime(String.valueOf(now));

        //保存文章信息
        jedisService.hset("article:" + article.getId(), "id", article.getId());
        jedisService.hset("article:" + article.getId(), "title", article.getTitle());
        jedisService.hset("article:" + article.getId(), "link", article.getLink());
        jedisService.hset("article:" + article.getId(), "poster", article.getPoster());
        jedisService.hset("article:" + article.getId(), "time", article.getTime());
        jedisService.hset("article:" + article.getId(), "votes", article.getVotes());

        //插入到时间排序文章列表
        jedisService.zadd("article:time", now, "article:" + article.getId());
        jedisService.zadd("article:score", now + VOTE_SCORE, "article:" + article.getId());
    }

    @Override
    public List<Article> getArticleOrderTime(Integer page) {
        int start = (page-1) * ARTICLES_PER_PAGE;
        int end = start + ARTICLES_PER_PAGE - 1;
        List<Article> articles = new ArrayList<>(ARTICLES_PER_PAGE);
        Set<String> ids = jedisService.zrevrange("article:time", start, end);
        for (String id: ids) {
            Map<String, String> map = jedisService.hgetAll(id);
            Article article = new Article();
            article.setId(map.get("id"));
            article.setTime(map.get("time"));
            article.setLink(map.get("link"));
            article.setPoster(map.get("poster"));
            article.setTitle(map.get("title"));
            article.setVotes(map.get("votes"));
            articles.add(article);
        }
        return articles;
    }

    @Override
    public List<Article> getArticleOrderVote(Integer page) {
        int start = (page-1) * ARTICLES_PER_PAGE;
        int end = start + ARTICLES_PER_PAGE - 1;
        List<Article> articles = new ArrayList<>(ARTICLES_PER_PAGE);
        Set<String> ids = jedisService.zrevrange("article:score", start, end);
        for (String id: ids) {
            Map<String, String> map = jedisService.hgetAll(id);
            Article article = new Article();
            article.setId(map.get("id"));
            article.setTime(map.get("time"));
            article.setLink(map.get("link"));
            article.setPoster(map.get("poster"));
            article.setTitle(map.get("title"));
            article.setVotes(map.get("votes"));
            articles.add(article);
        }
        return articles;
    }
}
