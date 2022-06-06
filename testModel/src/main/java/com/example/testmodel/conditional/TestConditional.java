package com.example.testmodel.conditional;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.AnnotationMetadata;

/**
 *   conditional 底层实现 demo
 */
public class TestConditional {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        StandardEnvironment environment = new StandardEnvironment();
        // 模拟配置文件
        environment.getPropertySources().addLast(new SimpleCommandLinePropertySource(
                "--spring.datasource.url=jdbc:mysql://localhost:3306/test",
                "--spring.datasource.username=root",
                "--spring.datasource.password=123456"));

        // 获取当前路径下的包名 ， 等同于源码 @AutoConfigurationPackage注解里的 AutoConfigurationPackages.Registrar.class
        String packageName = TestConditional.class.getPackage().getName();
        System.out.println(packageName);
        AutoConfigurationPackages.register(context.getDefaultListableBeanFactory(),packageName);


        context.setEnvironment(environment);
        // 注入常用的Bean后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean(Config.class);
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            String description = context.getBeanFactory().getBeanDefinition(name).getResourceDescription();
                System.out.println(name + "   来源  " + description);
        }


    }

    @Configuration
    @Import(MyImportSelector.class)
    static class Config{


    @Bean
    @TestConditionalOnClass(className = "com.alibaba.druid.pool.DruidDataSource",exists = true)
    public Bean1 bean1(){
        return new Bean1();
    }

     @Bean
     @TestConditionalOnClass(className = "com.alibaba.druid.pool.DruidDataSource",exists = false)
     public Bean2 bean2(){
         return new Bean2();
     }


        @Configuration
    static class AutoConfiguration2{};
    }

    static class MyImportSelector implements DeferredImportSelector{
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{};
        }
    }

    static class Bean1{};
    static class Bean2{};
}
