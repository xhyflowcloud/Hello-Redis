package lovenn.net;

import lovenn.net.domian.Article;
import lovenn.net.domian.User;
import lovenn.net.service.ArticleService;
import lovenn.net.util.Generator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringBootApplication.class})
//@PropertySource({"classpath:application.properties"})
public class SpringBootApplicationTest {

    @Resource
    private ArticleService articleService;
    @Resource
    private Generator defaultGenerator;

    @Test
    public void articlePostTest() {

        User user = new User();
        user.setName("sunyanan");
        user.setUserId(String.valueOf(defaultGenerator.generate()));
        Article article = new Article();
        article.setTitle("地球");
        article.setLink("http://wwww.baidu.com");
        articleService.post(user, article);
    }

    @Test
    public void articleGetTimeTest() {
        List<Article> articles = articleService.getArticleOrderTime(1);
        if(articles != null && articles.size() > 0) {
            for (Article article: articles)  {
                System.out.println(article);
            }
        }
    }
}
