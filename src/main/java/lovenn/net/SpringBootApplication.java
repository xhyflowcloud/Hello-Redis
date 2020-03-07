package lovenn.net;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.PropertySource;

@org.springframework.boot.autoconfigure.SpringBootApplication
@PropertySource({"classpath:application.properties"})
public class SpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}
