package com.example.testmodel.autoConfiguration;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.AnnotationMetadata;

/**
 *   测试 DataSource 的自动配置
 */
public class TestDataSourceAuto {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        StandardEnvironment environment = new StandardEnvironment();
        // 模拟配置文件
        environment.getPropertySources().addLast(new SimpleCommandLinePropertySource(
                "--spring.datasource.url=jdbc:mysql://localhost:3306/test",
                "--spring.datasource.username=root",
                "--spring.datasource.password=123456"));

        // 获取当前路径下的包名 ， 等同于源码 @AutoConfigurationPackage注解里的 AutoConfigurationPackages.Registrar.class
        String packageName = TestDataSourceAuto.class.getPackage().getName();
        System.out.println(packageName);
        AutoConfigurationPackages.register(context.getDefaultListableBeanFactory(),packageName);


        context.setEnvironment(environment);
        // 注入常用的Bean后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean(Config.class);
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            String description = context.getBeanFactory().getBeanDefinition(name).getResourceDescription();
            if (description != null) {
                System.out.println(name + "   来源  " + description);
            }
        }


    }

    @Configuration
    @Import(MyImportSelector.class)
    static class Config{

    }

    static class MyImportSelector implements DeferredImportSelector{
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{
                    DataSourceAutoConfiguration.class.getName(),
                    // @AutoConfigureAfter({DataSourceAutoConfiguration.class, MybatisLanguageDriverAutoConfiguration.class})
                    // 在某些Bean生成之后再生成，依赖关系
                    MybatisAutoConfiguration.class.getName(),
                    /**
                     *  事务注解的原理为， 切面 + 通知， 本质也是环绕通知，  前后加事务开启和关闭的方法
                     *
                    */
                    DataSourceTransactionManagerAutoConfiguration.class.getName(),
                    TransactionAutoConfiguration.class.getName()
            };
        }
    }
}
