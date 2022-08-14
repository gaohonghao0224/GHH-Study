package com.zc.demo.scope.invalid;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class F2 {
    @PreDestroy
    public void destroy(){
        System.err.println(" F2  销毁");
    }
}
