package com.zc.demo.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MyBean implements BeanNameAware , ApplicationContextAware , InitializingBean {

    // BeanNameAware 注入 当前类的名称
    @Override
    public void setBeanName(String name) {
        System.err.println("当前类的名称   --   " + this + "  --  " +  name);
    }

    // ApplicationContextAware 注入 当前容器类型
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.err.println("当前容器类型   --   " + applicationContext);

    }

    // InitializingBean     初始化方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.err.println("初始化方法   --  " + this + "    --  ");
    }
}
