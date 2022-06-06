package com.zc.demo.scope.invalid;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class F1 {
    @PreDestroy
    public void destroy(){
        System.err.println(" F1  销毁");
    }
}
