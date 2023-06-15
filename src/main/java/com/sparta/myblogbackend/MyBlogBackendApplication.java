package com.sparta.myblogbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyBlogBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBlogBackendApplication.class, args);
    }

}
