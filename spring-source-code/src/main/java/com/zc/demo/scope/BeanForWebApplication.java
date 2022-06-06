package com.zc.demo.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 *  请求作用域
 */
@Scope("application")
@Component
public class BeanForWebApplication {
    @PreDestroy
    public void destroy(){
        System.err.println(" BeanForSession  销毁");
    }
}
