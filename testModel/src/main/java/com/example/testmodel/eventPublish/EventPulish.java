package com.example.testmodel.eventPublish;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.List;

/**
 *  SpringApplicationRunListener 事件发布器
 *        事件的作用：
 *             事件的作用为就是， 有一些监听器会监听事件的发生，如
 *             environmentPrepared 事件， 此事件发生之后会被 environmentListener 监听到，从而去执行 environment的各种后处理器类
 *
 *             这些事件会随 springboot的12个启动步骤相呼应
 *
 *
 */
public class EventPulish {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        SpringApplication application = new SpringApplication();
        // 添加监听器
        application.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println(" ----------  事件陈列  --------------");
                System.out.println(event.getClass());
            }
        });

        // 获取事件发送器类名
        // 测试事件发布器
        List<String> names = SpringFactoriesLoader.loadFactoryNames(SpringApplicationRunListener.class,EventPulish.class.getClassLoader());
        for (String name : names) {
            System.out.println("事件发布器：  " + name);
            Class<?> clazz = Class.forName(name);
            Constructor<?> constructor = clazz.getConstructor(SpringApplication.class, String[].class);
            SpringApplicationRunListener publish = (SpringApplicationRunListener) constructor.newInstance(application,args);

            // 发布事件
            DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();
            publish.starting(bootstrapContext);  // spring boot 开始启动
            publish.environmentPrepared(bootstrapContext,new StandardEnvironment()); // 环境信息准备完毕
            // 准备一个spring 容器
            GenericApplicationContext context = new GenericApplicationContext();
            publish.contextPrepared(context);  // 在spring 容器创建，并调用了 Application初始化器之后会调用此事件
            publish.contextLoaded(context); // 所有BeanDefinition 加载完毕会执行此事件
            context.refresh();
            publish.started(context);  // spring容器初始化完成， 也就是 context.refresh方法调用完毕
            publish.running(context);   // spring boot 启动完毕

            /**

             */

            publish.failed(context,new Exception(" springboot 启动出错 ")); // springboot 启动出错



        }
    }
}
