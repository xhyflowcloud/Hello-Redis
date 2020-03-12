package lovenn.net.controller;

import lovenn.net.domian.Article;
import lovenn.net.domian.Login;
import lovenn.net.domian.User;
import lovenn.net.service.ArticleService;
import lovenn.net.service.LoginService;
import lovenn.net.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private HttpServletRequest request;

    @Resource
    private ArticleService articleService;

    @Resource
    private UserService userService;

    @Resource
    private LoginService loginService;



    @RequestMapping("/getOrderTime")
    public List<Article> getOrderTime() {
        Login login = getLoginInfo();
        List<Article> articles = articleService.getArticleOrderTime(userService.getUser(login == null ? null : login.getUserId()), 1);
        if(login != null && articles != null && !articles.isEmpty()) {
            List<String> ids = new ArrayList<>();
            for (Article article: articles) {
                ids.add(article.getId());
            }
            login.setItems(ids);
            loginService.updateLoginStatus(login);
        }
        return articles;
    }

    @RequestMapping("/getOrderScore")
    public List<Article> getOrderScore() {
        Login login = getLoginInfo();
        List<Article> articles = articleService.getArticleOrderScore(userService.getUser(login == null ? null : login.getUserId()), 1);
        if(login != null && articles != null && !articles.isEmpty()) {
            List<String> ids = new ArrayList<>();
            for (Article article: articles) {
                ids.add(article.getId());
            }
            login.setItems(ids);
            loginService.updateLoginStatus(login);
        }
        return articles;
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
        Login login = getLoginInfo();
        if(login == null) return "请先登录";
        User user = userService.getUser(login.getUserId());
        articleService.vote(user, article, 1);
        return "投票成功";

    }

    @RequestMapping("/vote/down")
    public String voteDown(@RequestBody Article article) {
        Login login = getLoginInfo();
        if(login == null) return "请先登录";
        User user = userService.getUser(login.getUserId());
        articleService.vote(user, article, -1);
        return "投票成功";
    }

    @RequestMapping("/login")
    public void login(@RequestBody User user) {
        //检查用户信息
        if (user == null || user.getName() == null) return;
        User user0 = userService.getUser(user.getName());
        if(user0 != null) {
            request.set
        }
    }

    private Login getLoginInfo() {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie: cookies) {
                if("token".equals(cookie.getName())) {
                    Login checkLogin = new Login();
                    checkLogin.setToken(cookie.getValue());
                    String userId = loginService.checkLoginStatus(checkLogin);
                    if(userId != null) {
                        checkLogin.setUserId(userId);
                    }
                    return checkLogin;
                }
            }
        }
        return null;
    }
}
