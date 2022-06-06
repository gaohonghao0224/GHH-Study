package com.zc.demo.ying;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Map;

public class TestBeanFactory {
    public static void main(String[] args) {

        // BeanFactory的一个实现类
        // 目前里面是空的
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

        // 定义一个 BeanDefinition , 也能理解为就是一个Bean,但是目前还没有注册到容器中
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        // 注册到容器 , 目前容器中已经有了一个 名称为  testConfig的bean
        // 但是,并没有Bean1 和 Bean2,说明目前的容器不具备解析 @Configuration 和 @Bean 注解的功能
        beanFactory.registerBeanDefinition("testConfig",beanDefinition);
//        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

        // 给BeanFactory添加常用的后处理器,使之功能增强
        // 看名字盲猜一手, 是Config相关的注解工具类,
        // 添加了一些 internalConfigurationAnnotationProcessor , internalAutowiredAnnotationProcessor 之类的Bean
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
//        Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(System.out::println);

        // BeanFactory后处理器的主要功能,补充了一些Bean定义
        Map<String, BeanFactoryPostProcessor> beansOfType = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        // 看打印能发现,beansOfType 里面有
        // org.springframework.context.annotation.internalConfigurationAnnotationProcessor  --  这个是解析Configuration注解的
        // org.springframework.context.event.internalEventListenerProcessor  -- 这个应该是 事件发布的 瞎猜的
        beansOfType.forEach((k,v) -> System.out.println( " test  " + k + "  =  " + v));
        // 这个感觉是将map里面的功能,通过后处理器,注册到了 实例化的beanFactory (容器)里面
        beansOfType.values().stream().forEach(b -> b.postProcessBeanFactory(beanFactory) );
        // 目前能打印出,此类中所有的bean
        Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(System.out::println);

    }

    @Configuration
    static class Config{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(){
            return new Bean2();
        }
    }

    static class Bean1{
        public Bean1(){
            System.out.println("Bean1 被初始化了 ... ");
        }
    }


    static class Bean2{
        public Bean2(){
            System.out.println("Bean2 被初始化了 ... ");
        }
    }
}
