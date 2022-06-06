package com.example.testmodel.springEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *  事件发布器 实现
 */
@Configuration
public class TestPublish1 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestPublish1.class);
        MyService bean = context.getBean(MyService.class);
        bean.doBusiness();
        context.close();
    }

    @Component
    static class MyService{
        @Autowired
        private ApplicationEventPublisher publisher;
        public void doBusiness(){
            System.out.println(" 主线业务1 ");
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

    /**
     * 自定义事件发布器，为了调用部分方法，给其余不用的方法空实现
     */
     abstract static class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster{
        @Override
        public void addApplicationListener(ApplicationListener<?> listener) {

        }

        @Override
        public void addApplicationListenerBean(String listenerBeanName) {

        }

        @Override
        public void removeApplicationListener(ApplicationListener<?> listener) {

        }

        @Override
        public void removeApplicationListenerBean(String listenerBeanName) {

        }

        @Override
        public void removeApplicationListeners(Predicate<ApplicationListener<?>> predicate) {

        }

        @Override
        public void removeApplicationListenerBeans(Predicate<String> predicate) {

        }

        @Override
        public void removeAllListeners() {

        }

        @Override
        public void multicastEvent(ApplicationEvent event) {

        }

        @Override
        public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {

        }
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(ConfigurableApplicationContext context){
        return new AbstractApplicationEventMulticaster(){
            // 监听器集合
            private List<GenericApplicationListener> listeners = new ArrayList<>();

            // 收集监听器， 在容器启动的时候，会检测所有实现了 ApplicationListener接口的类
            @Override
            public void addApplicationListenerBean(String listenerBeanName) {
                // 获取监听器类
                ApplicationListener listener = (ApplicationListener) context.getBean(listenerBeanName);
                System.err.println(listener);
                // 获取监听器支持的事件类型， 如自定义的 MyEvent， 不同的事件类型赋值的时候匹配会报错
                // 获取监听器类型，在获取接口，下标确定， 在获取接口里面的泛型，下标确定，默认为0
                ResolvableType type = ResolvableType.forClass(listener.getClass()).getInterfaces()[0].getGeneric();
                System.err.println(type);

                // 为了防止冲突，要有事件检查的环节，所以要是用 GenericApplicationListener 子类来获取， 是ApplicationListener的子类
                GenericApplicationListener genericApplicationListener = new GenericApplicationListener() {
                    /**
                     *
                     * @param eventType  当前事件
                     * @return
                     */
                    @Override
                    public boolean supportsEventType(ResolvableType eventType) {
                        return type.isAssignableFrom(eventType);
                    }

                    // 调用原始 listener的onApplicationEvent 方法
                    @Override
                    public void onApplicationEvent(ApplicationEvent event) {
                        listener.onApplicationEvent(event);

                    }
                };
                listeners.add(genericApplicationListener);
            }

            // 发事件的   Mysevice中的publishEvent 方法实际调用的就是此方法

            /**
             *  调用流程：
             *      Mysevice中的publishEvent 发布， multicastEvent 是实际执行的方法
             *      然后循环调用 GenericApplicationListener 里面已经保存的 listener 目前里面有三种， refresh ， Myevent, close 三种监听器
             *      然后 supportsEventType 方法判断 Myevent事件， 然后执行 onApplicationEvent 方法
             *      而 onApplicationEvent方法的实际执行在 实现了ApplicationListener的监听器中 也就是在 MailEventListener 中， 随即执行
             * @param event the event to multicast
             * @param eventType the type of event (can be {@code null})
             */
            @Override
            public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
                for (GenericApplicationListener listener : listeners) {
                    if (listener.supportsEventType(ResolvableType.forClass(event.getClass()))) {
                        listener.onApplicationEvent(event);
                    }
                }
            }
        };
    }



}
