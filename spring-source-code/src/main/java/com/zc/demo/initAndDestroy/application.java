package com.zc.demo.initAndDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(application.class, args);

        context.close();
    }
}
