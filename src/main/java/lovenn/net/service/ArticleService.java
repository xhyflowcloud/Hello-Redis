package lovenn.net.service;

import lovenn.net.domian.Article;
import lovenn.net.domian.User;

import java.util.List;

public interface ArticleService {

    /**
     * 投票
     */
    void vote(User user, Article article, Integer value);

    /**
     * 发布
     */
    void post(User user, Article article);

    /**
     * 获取文章 时间
     */
    List<Article> getArticleOrderTime(User user, Integer page);

    /**
     * 获取文章 分数
     */
    List<Article> getArticleOrderScore(User user, Integer page);
}
