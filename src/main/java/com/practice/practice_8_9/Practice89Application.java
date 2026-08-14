package com.practice.practice_8_9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Practice89Application {

    public static void main(String[] args) {
        SpringApplication.run(Practice89Application.class, args);
    }

}
