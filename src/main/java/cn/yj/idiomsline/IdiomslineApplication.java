package cn.yj.idiomsline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 为了捡起我的java，做了这个成语接龙的小练习。
 */
//@EnableJpaRepositories(basePackages = "cn.yj.idiomsline.repository")
@SpringBootApplication
public class IdiomslineApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdiomslineApplication.class, args);
    }

}
