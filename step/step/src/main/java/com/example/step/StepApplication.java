package com.example.step;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
public class StepApplication {

    public static void main(String[] args) {
        SpringApplication.run(StepApplication.class, args);
        StandardEnvironment standardEnvironment = new StandardEnvironment();
    }

}
