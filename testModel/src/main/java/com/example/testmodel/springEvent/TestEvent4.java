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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *      优化 TestEvent3的代码
 */
@Configuration
public class TestEvent4 {
    public static void main(String[] args) throws NoSuchMethodException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestEvent4.class);
        

        for (String name : context.getBeanDefinitionNames()) {
            Object bean = context.getBean(name);
            for (Method method : bean.getClass().getMethods()) {
                if (method.isAnnotationPresent(MyEventListener.class)) {
                    ApplicationListener listener =  new ApplicationListener() {
                        @Override
                        public void onApplicationEvent(ApplicationEvent event) {
                            System.out.println("事件类型  " + event.getClass());
                            // 获取方法参数类型
                            Class<?> parameterType = method.getParameterTypes()[0];
                            // 检测  参数类型是否可以复制给调用类型，说白了就是能不能赋值
                            if(parameterType.isAssignableFrom(event.getClass())){
                                try {
                                    method.invoke(bean,event);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                } catch (InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    };

                    /**
                     *  将 listener加入到context里面才能生效：
                     *      执行流程为：
                     *          先执行 doBusiness 方法，然后  publisher.publishEvent(new MyEvent("发布者   ")); 发布事件
                     *          然后被 监听器监听，
                     *          再然后执行里面所有的监听器，再然后执行里面的逻辑 （本范例是依靠反射去执行的逻辑）
                     *
                     *
                     */
                    context.addApplicationListener(listener);
                }

            }
        }

        context.getBean(MyService.class).doBusiness();
        /**
         *   在 context.close() ， 的时候会也会发布一个 ContextClosedEvent事件，同样会被捕捉到，只要是事件就会被context捕捉，所以要限定类型
         *   但是 我们自定义的 MyEvent，和  ContextClosedEvent 没有任何关系，所以类型会报错， 加上条件就好了
         *
         */
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
        @MyEventListener
        public void listen(MyEvent event) {
            System.out.println("MessageEventListener       发送邮件通知结果");
        }
    }

    @Component
    static class MailEventListener {
        @MyEventListener
        public void listen(MyEvent event) {
            System.out.println("MailEventListener       发送邮件通知结果");
        }

    }



    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface MyEventListener{}

}
