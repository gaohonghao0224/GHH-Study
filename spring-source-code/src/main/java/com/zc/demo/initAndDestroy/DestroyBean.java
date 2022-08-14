package com.zc.demo.initAndDestroy;

import org.springframework.beans.factory.DisposableBean;

import javax.annotation.PreDestroy;
/**
 *   Bean的销毁方法以及顺序
 */
public class DestroyBean implements DisposableBean {

    @PreDestroy
    public void destroy1(){
        System.out.println(" @PreDestroy 注解销毁方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean 接口销毁方法");
    }

    public void destroy3(){
        System.out.println("@Bean 注解 destroyMethod 属性 销毁方法");
    }
}
