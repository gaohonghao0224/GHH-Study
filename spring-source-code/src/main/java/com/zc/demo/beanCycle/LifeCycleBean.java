package com.zc.demo.beanCycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class LifeCycleBean {

    public LifeCycleBean(){
        System.out.println( "LifeCycleBean的构造方法...");
    }

    @Autowired
    public void autowired(@Value("${JAVA_HOME}") String home){
        System.out.println(home);
    }
    @PostConstruct
    public void init(){
        System.out.println("LifeCycleBean 的初始化方法");
    }

    @PreDestroy
    public void destroy(){
        System.out.println(" LifeCycleBean 的销毁方法");
    }
}
