package com.bookhub.bookhub_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class BookhubBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookhubBackendApplication.class, args);
    }

}
