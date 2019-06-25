package viewstar.doubleauth.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "viewstar.doubleauth.demo")
@EnableJpaRepositories(basePackages={"viewstar.doubleauth.demo.jpa.dao"})
@EntityScan("viewstar.doubleauth.demo.jpa.")
public class DoubleAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoubleAuthApplication.class, args);
    }

}
