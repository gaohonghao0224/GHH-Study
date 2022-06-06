package com.zc.demo.commonBeanPostProcessor;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *  实现 autowired注解功能
 */
public class TestAutowired {
    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.registerSingleton("bean2",new Bean2());
        beanFactory.registerSingleton("bean3",new Bean3());
        for(String name : beanFactory.getBeanDefinitionNames()) System.out.println(" --  " + name);

        // 注入 @Value 注解功能
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());

        // 注入 ${}解析器功能
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment() :: resolvePlaceholders);

        // 后处理器
        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        // 之所以要注入工厂，目的是为了给需要 autowired的属性提供bean实例，因为所有的bean实例都在容器中
        // 比如，  bean1 里面要注入 bean2，这里不是为了查找bean1，是为了查找bean2的，bean1是需求方,beanFactory是提供方
        processor.setBeanFactory(beanFactory);

        // 实例化bean1
        Bean1 bean1 = new Bean1();
//        System.out.println(bean1);
        // 注入 bean1 中需要自动装配的属性及方法
//        processor.postProcessProperties(null,bean1,"bean4");
//        System.out.println(bean1);

        // 反射调用
        Method findAutowiringMetadata = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod("findAutowiringMetadata", String.class,Class.class, PropertyValues.class);
        findAutowiringMetadata.setAccessible(true);
        // 查找bean1中标注了@Autowired的注解的属性和方法
        // 会以数组形式显示出 属性和方法的集合
        InjectionMetadata injectionMetadata = (InjectionMetadata) findAutowiringMetadata.invoke(processor, "bean1", Bean1.class, null);
//        System.out.println(injectionMetadata);

        // 注入bean1 中@Autowired修饰的类和属性  进行依赖注入，根据类型注入
        injectionMetadata.inject(bean1,"bean4",null);
//        System.out.println(bean1);


        //   ************  按照类型查找值 原理实现  ***********

        /**
         *          获取属性注入的 bean实例
          */
        Field bean3 = Bean1.class.getDeclaredField("bean3");
        bean3.setAccessible(true);
        DependencyDescriptor dependencyDescriptor = new DependencyDescriptor(bean3,false);
        // 根据获取到的属性值的class，去容器内查找，返回
        Object o = beanFactory.doResolveDependency(dependencyDescriptor, null, null, null);
        System.out.println(o);


        // 根据 autowired修饰的方法，依赖注入
        // 一般都是set方法，参数只有一个，默认为0，不然就是 @Bean里面的参数注入，也是只有一个，感觉默认是给一个参数方法使用的 （我自己猜的）
        Method setBean2 = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
        bean3.setAccessible(true);
        // new MethodParameter 第一个参数为 方法实例，第二个为 给第几个参数注入属性，下标
        DependencyDescriptor dependencyDescriptor3 = new DependencyDescriptor(new MethodParameter(setBean2,0),false);
        // 根据获取到的属性值的class，去容器内查找，返回
        Object o3 = beanFactory.doResolveDependency(dependencyDescriptor3, null, null, null);
        System.out.println(o3);

    }
}
