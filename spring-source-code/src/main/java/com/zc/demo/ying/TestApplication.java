package com.zc.demo.ying;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.Controller;

import javax.activation.FileDataSource;
import java.util.Arrays;

public class TestApplication {
    public static void main(String[] args) {
        // 此方法目前为空容器
//        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//        // 根据xml读取bean定义信息
//        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
//        // 将读取到的bean定义信息加载到容器中
////        FileDataSource fileDataSource = new FileDataSource("testApplication.xml");  // 这是读取系统路径下的方法
//        reader.loadBeanDefinitions(new ClassPathResource("testApplication.xml"));
//        for (String name : beanFactory.getBeanDefinitionNames()) System.out.println(name);
//                testClassPathXmlApplicationContext();
//        testAnnotationConfigApplicationContext();
        testAnnotationConfigServletWebApplicationContext();
    }

    public static void testClassPathXmlApplicationContext(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("testApplication.xml");
        for (String name : context.getBeanDefinitionNames()) System.out.println(name);
    }

    public static void testFileSystemXmlApplicationContext(){
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("testApplication.xml");
        for (String name : context.getBeanDefinitionNames()) System.out.println(name);
    }

    public static void testAnnotationConfigApplicationContext(){
        // 根据注解配置类生成容器 @Configuration
        // 此接口默认会生成一些常用的后处理器Bean 如 internalConfigurationAnnotationProcessor ， internalAutowiredAnnotationProcessor 等
        // 配置类会自动加载成Bean
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
    }

    public static void testAnnotationConfigServletWebApplicationContext(){
        // 此接口默认会生成一些常用的后处理器Bean 如 internalConfigurationAnnotationProcessor ， internalAutowiredAnnotationProcessor 等
        AnnotationConfigServletWebServerApplicationContext webServerApplicationContext = new AnnotationConfigServletWebServerApplicationContext(webConfig.class);
        Arrays.stream(webServerApplicationContext.getBeanDefinitionNames()).forEach(System.out::println);
    }

    @Configuration
    static class webConfig{
        // 创建内置的 tomcat 服务器
        @Bean
        public ServletWebServerFactory tomcatServletWebServerFactory(){
            TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
            tomcatServletWebServerFactory.setPort(8081);
            return tomcatServletWebServerFactory;
        }
        // 创建 DispatcherServlet 前端控制器，所有的请求都有此前端控制器接收，然后分发到具体的控制器上
        @Bean
        public DispatcherServlet dispatcherServlet(){
            return new DispatcherServlet();
        }
        // 将 DispatcherServlet 前端控制器 注册到 Tomcat服务器里面
        @Bean
        public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet){
            // 定义 前端控制器的接收路径
            return new DispatcherServletRegistrationBean(dispatcherServlet,"/");
        }
        // 定义控制器
        // 可以在Bean注解上定义路径，默认规约为，只要是 / 开头的，就会被看作一个路径
        @Bean("/love")
        public Controller controller(){
            return (request, response) -> {
                response.setContentType("text/html;charset=UTF-8");
               response.getWriter().println("我爱你，myx");
                return null;
            };
        }



    }


    @Configuration
    static class Config{
        @Bean
        public Bean1 bean1(){return new Bean1();}

        @Bean
        public Bean2 bean2(Bean1 bean1){
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        }
    }

     static class Bean1{
        public Bean1(){
            System.out.println("Bean1 被初始化了 ... ");
        }
     }

    static class Bean2 {
        public Bean2() {
            System.out.println("Bean2 被初始化了 ... ");
        }

        private Bean1 bean1;

        public Bean1 getBean1() {
            return bean1;
        }

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }

    }
}
