package lovenn.net.service;

import lovenn.net.domian.Article;
import lovenn.net.domian.User;

import java.util.List;

public interface ArticleService {

    /**
     * 投票
     */
    void vote(User user, Article article);

    /**
     * 发布
     */
    void post(User user, Article article);

    /**
     * 获取文章 时间
     * @return
     */
    List<Article> getArticleOrderTime(Integer page);

    /**
     * 获取文章 分数
     * @return
     */
    List<Article> getArticleOrderVote(Integer page);
}
