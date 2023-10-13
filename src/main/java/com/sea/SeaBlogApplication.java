package com.sea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sea.config")
public class SeaBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeaBlogApplication.class, args);
    }

}
