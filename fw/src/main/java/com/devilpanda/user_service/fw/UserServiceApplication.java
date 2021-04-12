package com.devilpanda.user_service.fw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.devilpanda.user_service")
@EnableJpaRepositories(basePackages = "com.devilpanda.user_service.adapter.jpa")
@EntityScan(basePackages = "com.devilpanda.user_service.domain")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
