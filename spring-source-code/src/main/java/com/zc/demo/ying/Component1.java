package com.zc.demo.ying;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Component1 {

    @Autowired
    private ApplicationEventPublisher context;

    public void register(){
        System.out.println("先吃饭");
        // 事件的作用主要为解耦合
        // 自动去找@EventListener 注解 对应 UserRegisteredEvent 参数类型的方法
        // 需要一个ApplicationEvent对象,对象里面的参数可以理解为要传递的对象, 比如用户信息等
        context.publishEvent(new UserRegisteredEvent(context));
    }
}
