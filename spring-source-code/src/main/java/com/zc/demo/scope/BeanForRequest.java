package com.zc.demo.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 *  请求作用域
 */
@Scope("request")
@Component
public class BeanForRequest {
    @PreDestroy
    public void destroy(){
        System.err.println(" BeanForRequest  销毁");
    }
}
