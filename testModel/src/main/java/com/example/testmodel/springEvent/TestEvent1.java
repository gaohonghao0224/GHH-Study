package com.example.testmodel.springEvent;

import org.apache.catalina.core.ApplicationPushBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *  接口实现监听器
 */
@Configuration
public class TestEvent1 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestEvent1.class);
        MyService bean = context.getBean(MyService.class);
        bean.doBusiness();
        context.close();
    }

    @Component
    static class MyService{
        @Autowired
        private ApplicationEventPublisher publisher;
        public void doBusiness(){
            System.out.println(" 主线业务");
            publisher.publishEvent(new MyEvent("发布者   "));
        }
    }

    /**
     *   创建 自定义事件类型
     */
    static class MyEvent extends ApplicationEvent {

        // source 是具体的逻辑
        public MyEvent(Object source) {
            super(source);
        }
    }

    /**
     *  创建事件接收器，
     *      可以添加泛型，泛型是具体的 事件类型，意义为 只接受MyEvent事件的类型
     *
     *      发送信息事件接收器
     */
    @Component
    static class MessageEventListener implements ApplicationListener<MyEvent>{
        @Override
        public void onApplicationEvent(MyEvent event) {
            System.out.println(event.getSource() + "   MessageEventListener");
            System.out.println("MessageEventListener       发送信息通知结果");
        }
    }

    /**
     *  发送邮件事件接收器， 同上
     */
    @Component
    static class MailEventListener implements ApplicationListener<MyEvent>{
        @Override
        public void onApplicationEvent(MyEvent event) {
            System.out.println(event.getSource() + "        MailEventListener");
            System.out.println("MailEventListener       发送邮件通知结果");
        }
    }

}
