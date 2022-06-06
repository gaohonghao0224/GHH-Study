package com.zc.demo.beanFactoryPostProcessor;

import com.zc.demo.beanFactoryPostProcessor.config.config1;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

/**
 * 常见的工厂后处理器
 */
public class application_05 {
    public static void main(String[] args) throws IOException {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config1",config1.class);

        // ConfigurationClassPostProcessor 后处理器用于解析  @ComponentScan  @Component  @Bean 等注解
        // context.registerBean(ConfigurationClassPostProcessor.class);

        // 自定义后处理器   处理 @ComponentScan 注解
        context.registerBean(TestBeanFactoryPostProcessor.class);

        // 自定义后处理器  处理 @Bean 注解
        context.registerBean(TestAtBeanPostProcess.class);

        // 解析mapper注解  @MapperScan 注解
        //  需要获取包名，此后处理器也会生成一些常用的后处理器
//        context.registerBean(MapperScannerConfigurer.class,bd ->{
//            bd.getPropertyValues().add("basePackage","com.zc.demo.beanFactoryPostProcessor.mapper");
//        } );




        context.refresh();
        for(String name : context.getBeanDefinitionNames())  System.out.println(name);


    }
}
