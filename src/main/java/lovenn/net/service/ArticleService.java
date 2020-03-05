package lovenn.net.service;

import lovenn.net.domian.Article;
import lovenn.net.domian.User;

public interface ArticleService {

    /**
     * 投票
     */
    void vote(User user, Article article);
}
