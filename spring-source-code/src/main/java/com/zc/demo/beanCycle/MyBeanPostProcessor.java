package com.zc.demo.beanCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionUsageException;

/**
 * Bean 后处理器类
 */
@Component
public class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
        System.out.println(" <<<<<<<<<<      实例化Bean之前执行...");
        // 在这里 return 的对象会替换掉 lifeCycleBean的实例，返回null，为不替换
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            System.out.println(" <<<<<<<<<<      实例化Bean之后执行...");
        // 在这里 return 为true的话，正常执行， 为false的话， 跳过依赖注入阶段
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            System.out.println(" <<<<<<<<<<      依赖注入阶段执行...");
        // Autowired Value Resource 都算是依赖注入阶段
        return pvs;
    }



    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            System.out.println(" <<<<<<<<<<      初始化方法之前执行...");
        // 在这里return的对象会替换掉原来的Bean 如 @PreConstruct @ConfigurationProperties 等注解，
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            System.out.println(" <<<<<<<<<<      初始化方法之后执行...");
        // 在这里return的对象会替换掉原来的Bean， 如 代理增强等操作
        return null;
    }

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (beanName.equals("lifeCycleBean"))
            System.out.println(" <<<<<<<<<<     销毁Bean之前执行...");
    }

}
