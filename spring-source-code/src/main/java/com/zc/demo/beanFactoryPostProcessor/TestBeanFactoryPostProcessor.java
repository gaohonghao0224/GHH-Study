package com.zc.demo.beanFactoryPostProcessor;

import com.zc.demo.beanFactoryPostProcessor.config.config1;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 *  实现 @ComponentScan 原理
 */

public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {

            AnnotationBeanNameGenerator generator = new AnnotationBeanNameGenerator();
            // 查找指定类里面有没有对应的注解
            ComponentScan componentScan = AnnotationUtils.findAnnotation(config1.class, ComponentScan.class);
            // componentScan.basePackages 获取 ComponentScan 注解的 扫描包路径
            for (String url : componentScan.basePackages()) {
                String path = "classpath*:" + url.replace(".", "/") + "/**/*.class";

                CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
                // 通过Resource 获取指定路径下所有类
                Resource[] resources = new PathMatchingResourcePatternResolver().getResources(path);
                for (Resource resource : resources) {
//                System.out.println(r);
                    // 获取数据的元数据
                    MetadataReader reader  = factory.getMetadataReader(resource);
//                System.out.println("类名： " + reader.getClassMetadata().getClassName());
//                System.out.println(" 是否加了 @Component " +  reader.getAnnotationMetadata().hasAnnotation(Component.class.getName()));
//                // hasMetaAnnotation 是否包含某些注解
//                System.out.println("是否加了 @Controller " + reader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName()));
                    if (reader.getAnnotationMetadata().hasAnnotation(Component.class.getName()) || reader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName())) {
                        // bean定义，需要一个类的全限定名，来包装成bean
                        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(reader.getClassMetadata().getClassName()).getBeanDefinition();
                        if(configurableListableBeanFactory instanceof DefaultListableBeanFactory){
                            DefaultListableBeanFactory  beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
                            String beanName = generator.generateBeanName(beanDefinition, beanFactory);
                            beanFactory.registerBeanDefinition(beanName, beanDefinition);
                        }

                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
