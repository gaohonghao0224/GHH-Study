package com.zc.demo.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 *  多例
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class BeanForPrototype {
    @PreDestroy
    public void destroy(){
        System.err.println(" BeanForPrototype  销毁");
    }
}
