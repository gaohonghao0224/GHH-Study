package com.zc.demo.beanCycle;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板方法
 *      就是在某些类里面，我有自己固定的一套逻辑，而根据需求我需要像逻辑中间加某些功能，在不改变源码的情况下添加信息
 *      比如： spring的bean后处理器， beanFactory的getBean是固定的，但是根据我加的bean后处理器让bean能实现自动注入等功能
 *      不加就没有。
 *
 *      总体思想：
 *          有静有动， 静的是固定的底层逻辑，动的是附加功能
 */
public class TestMethodTemplate {
    public static void main(String[] args) {

        MyBeanFactory myBeanFactory = new MyBeanFactory();
        myBeanFactory.addBeanPostProcessor(f -> System.out.println("aotuWired"));
        myBeanFactory.getBean();
    }

    static class MyBeanFactory{
        public  Object getBean(){
            Object bean = new Object();
            System.out.println("构造 " + bean);
            System.out.println("注入 " + bean);
            for (BeanPostProcessor beanPostProcessor : list){
                beanPostProcessor.inject(bean);
            }
            System.out.println("初始化 " + bean);
            return bean;
        }
        //
        private List<BeanPostProcessor> list = new ArrayList<>();
        public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){list.add(beanPostProcessor);};
    }

    // 自定义的Bean后处理器方法
    static interface BeanPostProcessor{
        public void inject(Object bean);
    }
}
