package edu.nju;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("edu.nju")
@EntityScan("edu.nju")
@EnableJpaRepositories("edu.nju")
public class GmairDataCompletionApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmairDataCompletionApplication.class, args);
    }

}
