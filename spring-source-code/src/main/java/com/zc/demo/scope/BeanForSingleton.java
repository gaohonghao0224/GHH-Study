package com.zc.demo.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 *  εδΎ
 */
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class BeanForSingleton {
    @PreDestroy
    public void destroy(){
        System.err.println(" BeanForSingleton  ιζ―");
    }
}
