package com.zc.demo.commonBeanPostProcessor;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

//@SpringBootApplication
public class beanPostProcessorApplication {
    public static void main(String[] args) {
        // GenericApplicationContext 是一个 “干净” 的容器, 没有任何bean定义
        GenericApplicationContext context = new GenericApplicationContext();
//        for(String bean : context.getBeanDefinitionNames()) System.out.println(bean);

        // 使用原始方法添加bean
        // 但是没有触发 bean1的 生命周期方法
        context.registerBean("bean1",Bean1.class);
        context.registerBean("bean2",Bean2.class);
        context.registerBean("bean3",Bean3.class);
//        for(String bean : context.getBeanDefinitionNames()) System.out.println(bean);

        // 具体作用不明确，在这里是为了使 @value 注解生效
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());

        // 添加 AutowiredAnnotationBeanPostProcessor bean后处理器
        // 作用于  -- @Autowired  @Value
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);

        // 添加 AutowiredAnnotationBeanPostProcessor bean后处理器
        // 作用于  -- @Resource @PostConstruct  @Predestory
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        // 注册ConfigurationPropertiesBindingPostProcessor 后处理器
        // 作用于 -- spring-boot 的属性装配 @ConfigurationProperties 注解
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());

        // 初始化容器， 执行 beanFactory后处理器，添加bean后处理器，初始化所有单例
        context.refresh();

        for(String bean : context.getBeanDefinitionNames()) System.out.println(bean);

        context.close();




    }
}
