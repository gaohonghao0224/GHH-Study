package com.example.testmodel.springProxyFeatures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Bean1 {

    // 这里不用 private 是为了在别处能调用此属性，测试用
    protected Bean2 bean2;

    protected boolean initialized;


    /**
     *  此方法也不会被增强，说明
     *  在Autowired阶段 是影响的 原始对象
     */
    @Autowired
    public void setBean2(Bean2 bean2){
        System.out.println(" setBean(Bean2 bean2) ... ");
        this.bean2 = bean2;
    }


    /**
     *  此方法不会被增强，说明
     *  在 初始化阶段，是影响的 原始对象
     */
    @PostConstruct
    public void init(){
        System.out.println("Bean1 init() ... ");
        this.initialized = true;
    }

    public Bean2 getBean2(){
        System.out.println(" getBean2() ... ");
        return this.bean2;
    }

    public void m1(){
        System.out.println(" m1()  成员方法");
    }

    final public void m2(){
        System.out.println(" m2()  final 方法");
    }

    static public void m3(){
        System.out.println(" m3()  static 方法");
    }

    private void m4(){
        System.out.println(" m4()   private  方法 ");
    }
}
