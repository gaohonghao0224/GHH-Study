package com.example.testmodel.springEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *  注解 模式 监听事件
 */
@Configuration
public class TestEvent2 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestEvent2.class);
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
     *  @EventListener 注解的作用就是此方法为监听器方法， 参数为具体的事件
     */
    @Component
    static class MessageEventListener{
        @EventListener
        public void listen(MyEvent event) {
            System.out.println("MessageEventListener       发送邮件通知结果");
        }
    }

    @Component
    static class MailEventListener {
        @EventListener
        public void listen(MyEvent event) {
            System.out.println("MailEventListener       发送邮件通知结果");
        }
    }


}
