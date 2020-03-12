package lovenn.net.controller;

import lovenn.net.domian.Article;
import lovenn.net.domian.Login;
import lovenn.net.domian.User;
import lovenn.net.service.ArticleService;
import lovenn.net.service.LoginService;
import lovenn.net.service.UserService;
import lovenn.net.util.SimpleMD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        if (login != null && articles != null && !articles.isEmpty()) {
            List<String> ids = new ArrayList<>();
            for (Article article : articles) {
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
        if (login != null && articles != null && !articles.isEmpty()) {
            List<String> ids = new ArrayList<>();
            for (Article article : articles) {
                ids.add(article.getId());
            }
            login.setItems(ids);
            loginService.updateLoginStatus(login);
        }
        return articles;
    }

    @RequestMapping("/post")
    public String post(@RequestBody Article article) {
        Login login = getLoginInfo();
        if (login == null) return "请先登录";
        User user = userService.getUser(login.getUserId());
        articleService.post(user, article);
        return "发布成功";
    }

    @RequestMapping("/vote/up")
    public String voteUp(@RequestBody Article article) {
        Login login = getLoginInfo();
        if (login == null) return "请先登录";
        User user = userService.getUser(login.getUserId());
        articleService.vote(user, article, 1);
        return "投票成功";

    }

    @RequestMapping("/vote/down")
    public String voteDown(@RequestBody Article article) {
        Login login = getLoginInfo();
        if (login == null) return "请先登录";
        User user = userService.getUser(login.getUserId());
        articleService.vote(user, article, -1);
        return "投票成功";
    }

    @RequestMapping("/login")
    public String login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        //检查用户信息
        if (user == null || user.getUserId() == null) return "登录失败";
        User user0 = userService.getUser(user.getUserId());
        if (user0 != null) {
            Cookie cookie = new Cookie("token", SimpleMD5Util.encrypt(user.getUserId()));
            cookie.setMaxAge(60*5);
            response.addCookie(cookie);
            Login login = new Login();
            login.setUserId(user.getUserId());
            login.setToken(SimpleMD5Util.encrypt(user.getUserId()));
            loginService.updateLoginStatus(login);
            return "SUCCESS";
        }
        return "登录失败";
    }

    private Login getLoginInfo() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    Login checkLogin = new Login();
                    checkLogin.setToken(cookie.getValue());
                    String userId = loginService.checkLoginStatus(checkLogin);
                    if (userId != null) {
                        checkLogin.setUserId(userId);
                    }
                    return checkLogin;
                }
            }
        }
        return null;
    }
}
