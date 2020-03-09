package lovenn.net.controller;

import lovenn.net.domian.Article;
import lovenn.net.domian.User;
import lovenn.net.service.ArticleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @RequestMapping("/getOrderTime")
    public List<Article> getOrderTime() {
        return articleService.getArticleOrderTime(getUser(), 1);
    }

    @RequestMapping("/getOrderScore")
    public List<Article> getOrderScore() {
        return articleService.getArticleOrderScore(getUser(), 1);
    }

    @RequestMapping("/post")
    public String post(@RequestBody Article article) {
        User user = getUser();
        articleService.post(user, article);
        return "10000";
    }

    @RequestMapping("/vote/up")
    public String voteUp(@RequestBody Article article) {
        articleService.vote(getUser(), article, 1);
        return "10000";
    }

    @RequestMapping("/vote/down")
    public String voteDown(@RequestBody Article article) {
        articleService.vote(getUser(), article, -1);
        return "10000";
    }

    private User getUser() {
        User user = new User();
        user.setName("sunyanan");
        user.setUserId("10000000");
        return user;
    }
}
