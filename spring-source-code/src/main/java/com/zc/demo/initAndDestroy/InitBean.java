package com.zc.demo.initAndDestroy;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 *   Bean的初始化方法以及顺序
 */
public class InitBean implements InitializingBean {

   static {
        System.out.println("静态块");
    }

    @PostConstruct
    public void init1(){
        System.out.println( "@PostConstruct  注解初始化");
    }

    /**
     *  Aware接口注入内置属性的顺序在   @PostConstruct 和 InitializingBean 之间！！
     * @throws Exception
     */


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println( "InitializingBean  接口初始化");
    }

    public void init3(){
        System.out.println(" @Bean initMethod 属性初始化");
    }
}
