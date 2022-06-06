package com.example.testmodel.autoConfiguration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.List;

/**
 *   测试 Aop的自动配置
 */
public class TestAopAuto {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        StandardEnvironment environment = new StandardEnvironment();
        // 测试 AopAutoConfiguration  条件装配
        // 模拟配置文件
        environment.getPropertySources().addLast(new SimpleCommandLinePropertySource("--spring.aop.auto=false"));
        context.setEnvironment(environment);
        // 注入常用的Bean后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context);
        context.registerBean(Config.class);
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }


    }

    @Configuration
    @Import(MyImportSelector.class)
    static class Config{

    }

    /**
     *  获取第三方配置类的工具， 配合Import注解使用的
     *
     *      ImportSelector 接口直接获取第三方的Bean， 会在 自定义的Bean加载之前加载第三封的Bean
     *                      然后自定义的Bean如果和第三方的Bean 冲突的话，会重置掉第三方的Bean
     *
     *      DeferredImportSelector  延迟加载， 会后加载第三方的Bean，冲突的话，第三方Bean会重置掉我们自定义的Bean
     *
     *      返回值为第三方配置类的类名
     */
    static class MyImportSelector implements DeferredImportSelector{
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//            List<String> list = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, null);
//            return list.toArray(new String[0]);
            return new String[]{AopAutoConfiguration.class.getName()};
        }
    }
}
