package edu.nju.gmairdatacompletion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan("edu.nju")
@EnableMongoRepositories("edu.nju.mongo.repository")
public class GmairDataCompletionApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmairDataCompletionApplication.class, args);
    }

}
