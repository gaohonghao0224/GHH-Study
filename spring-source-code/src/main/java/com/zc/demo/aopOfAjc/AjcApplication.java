package com.zc.demo.aopOfAjc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *   运行时需要在 VM options 里面加入 -javaagent:D:\maven-repository/org/aspectjweaver/1.9.7/aspectjweaver-1.9.7.jar
 *
 *   D:\maven-repository 为你当前的仓库目录地址
 */
@SpringBootApplication
public class AjcApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AjcApplication.class, args);

        MyService myService = context.getBean(MyService.class);
        System.err.println(myService.getClass());
        myService.foo();
    }
}
