package com.zc.demo.beanFactoryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.Set;

/**
 * @Bean 注解的实现
 */
public class TestAtBeanPostProcess  implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {
            // 元数据读取流的工厂
            CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
            // 创建元数据读取流
            MetadataReader reader = factory.getMetadataReader(new ClassPathResource("com/zc/demo/beanFactoryPostProcessor/config/config1.class"));
            // 获取标注了 @Bean注解的方法 set集合
            Set<MethodMetadata> methods = reader.getAnnotationMetadata().getAnnotatedMethods(Bean.class.getName());
            for(MethodMetadata metadata : methods){
                // 获取注解 initMethod属性值
                String initMethod = metadata.getAnnotationAttributes(Bean.class.getName()).get("initMethod").toString();
                // 创建 BeanDefinition
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
                // 配置自动注入模型 构造方法注入选择 AUTOWIRE_CONSTRUCTOR
                builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
                // bean的名称为工厂方法名， 第二个参数为获取此方法具体的实例
                // 可以理解为工厂方法，config1就是那个工厂
                builder.setFactoryMethodOnBean(metadata.getMethodName(),"config1");
                if(!initMethod.isEmpty()){
                    // 注入bean的初始化方法 , 对应 @Bean -- initMethod 属性
                    builder.setInitMethodName(initMethod);
                }
                if(configurableListableBeanFactory instanceof DefaultListableBeanFactory){
                    DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
                    beanFactory.registerBeanDefinition(metadata.getMethodName(),builder.getBeanDefinition());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
