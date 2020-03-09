package lovenn.net.controller;

import lovenn.net.domian.Article;
import lovenn.net.domian.User;
import lovenn.net.service.ArticleService;
import lovenn.net.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Resource
    private UserService userService;

    @RequestMapping("/getOrderTime")
    public List<Article> getOrderTime(@RequestBody User user) {
        if(user != null && user.getName() != null) user = userService.getUser(user.getName());
        return articleService.getArticleOrderTime(user, 1);
    }

    @RequestMapping("/getOrderScore")
    public List<Article> getOrderScore(@RequestBody User user) {
        if(user != null && user.getName() != null) user = userService.getUser(user.getName());
        return articleService.getArticleOrderScore(user, 1);
    }

    @RequestMapping("/post")
    public String post(@RequestBody Article article) {
        if (userService.isLogin(article.getName())) {
            User user = userService.getUser(article.getName());
            articleService.post(user, article);
            return "发布成功";
        }
        return "请先登录";
    }

    @RequestMapping("/vote/up")
    public String voteUp(@RequestBody Article article) {
        if (userService.isLogin(article.getName())) {
            User user = userService.getUser(article.getName());
            articleService.vote(user, article, 1);
            return "投票成功";
        }
        return "请先登录";
    }

    @RequestMapping("/vote/down")
    public String voteDown(@RequestBody Article article) {
        if (userService.isLogin(article.getName())) {
            User user = userService.getUser(article.getName());
            articleService.vote(user, article, -1);
            return "投票成功";
        }
        return "请先登录";
    }

    @RequestMapping("/login")
    public User login(@RequestBody User user) {
        if (user == null) return null;
        User user1 = userService.getUser(user.getName());
        if (user1 != null) {
            userService.login(user1);
        }
        if (userService.isLogin(user.getName())) {
            user.setState("stated");
            return user;
        }
        return null;
    }

}
