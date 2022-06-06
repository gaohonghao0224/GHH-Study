package com.zc.demo.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 *  单例
 */
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class BeanForSingleton {
    @PreDestroy
    public void destroy(){
        System.err.println(" BeanForSingleton  销毁");
    }
}
