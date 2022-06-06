package com.example.testmodel.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

@Configuration
public class config1 {
    public static void main(String[] args) {
        System.out.println(" 1. 演示获取 Bean Definition 源");
        //  意思就是从哪里获取到 Bean， 一般都是随引导类位置去获取，也可以添加其他的source，根据xml去获取等等
        // 就是说可以根据各种的来源去添加Bean
        //        spring.setSources("");
        SpringApplication spring = new SpringApplication(config1.class);
        System.out.println(" 2. 演示推断应用类型");
        // 根据jar包准备容器类型
        System.out.println(" 3. 演示 ApplicationContext 初始化器");
        /**
         *          以上工作准备好之后，开始创建Appcation容器，这个位置给context的容器做扩展，开始添加一些初始化器
         *          spring.addInitializers 添加初始化器
          */
        spring.addInitializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
            @Override
            public void initialize(ConfigurableApplicationContext applicationContext) {
                if(applicationContext instanceof GenericApplicationContext){
                    GenericApplicationContext gac = (GenericApplicationContext) applicationContext;
                    gac.registerBean("bean3",Bean3.class);
                 }

            }
        });
        System.out.println(" 4. 演示监听器与事件");
        // 添加监听器
        spring.addListeners(event -> {
            System.out.println("事件为  " + event.getClass());
        });
        System.out.println(" 5. 演示主类推断");
        // 推断 main方法所在的类在哪里

        // 以上只是做了 springBoot的准备工作，并没有创建spring容器


        // 测试区域
        ConfigurableApplicationContext context = spring.run(args);
        for(String name: context.getBeanDefinitionNames()){
            System.out.println(name + " :   " + context.getBeanFactory().getBeanDefinition(name).getResourceDescription());
        }

    }

    static class Bean1 {
    }

    ;

    static class Bean2 {
    }

    ;

    static class Bean3 {
    }

    ;

    @Bean
    public Bean2 bean2(){
        return new Bean2();
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(){
        return new TomcatServletWebServerFactory();
    }
}