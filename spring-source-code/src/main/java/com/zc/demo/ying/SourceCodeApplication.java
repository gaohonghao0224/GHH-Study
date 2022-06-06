package com.zc.demo.ying;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.lang.reflect.Field;
import java.util.Map;

//@SpringBootApplication
public class SourceCodeApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(SourceCodeApplication.class, args);
//        ApplicationContext context2 = SpringApplication.run(SourceCodeApplication.class, args);

//        context.getBean("a");

        // 反射调用 DefaultSingletonBeanRegistry 类的私有属性 singletonObjects
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        // 需要使用BeanFactory来调用， 因为反射获取属性值需要一个实例
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> map = (Map<String, Object>)singletonObjects.get(beanFactory);
        // 只查看自定义的bean
//        map.entrySet().stream().filter(e -> e.getKey().startsWith("component")).forEach(s -> System.out.println(s.getKey() + " = " + s.getValue()));


        Resource[] resources1 = context.getResources("classpath:application.properties");
//        Resource[] resources1 = context.getResources("classpath:application.yml");

//        System.out.println(resources1);
        // 此方法源自 ResourcePatternResolver 接口， 作用为可以通配符寻找文件，如配置文件
        // file 是寻找磁盘路径下的文件，classpath 是寻找类路径下的文件， classpath* 是寻找jar包下的文件
        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
//            System.out.println(resource);
        }

        String javaHome = context.getEnvironment().getProperty("java_home");
        String port = context.getEnvironment().getProperty("server.port");
//        System.out.println(javaHome);
//        System.out.println(port);

        // 添加一个事件
        // 具体的参数值其实无所谓,只要给方法上面加上 @EventListener 注解, 并且方法参数对应的类型匹配,父子也算,
        // 启动的时候就会加载对应的方法
        context.publishEvent(new Integer(12));

        context.getBean(Component1.class).register();
    }

}
