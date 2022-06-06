package com.zc.demo.ying;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Component2 {

    // EventListener 注解标记这是一个事件
    @EventListener
    public void aa (UserRegisteredEvent event){
        System.out.println("事件  " + event);
        System.out.println("在拉屎");
    }
}
