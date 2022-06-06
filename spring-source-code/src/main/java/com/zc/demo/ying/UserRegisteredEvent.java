package com.zc.demo.ying;

import org.springframework.context.ApplicationEvent;


public class UserRegisteredEvent extends ApplicationEvent {

    public UserRegisteredEvent(Object source) {
        super(source);
    }
}
