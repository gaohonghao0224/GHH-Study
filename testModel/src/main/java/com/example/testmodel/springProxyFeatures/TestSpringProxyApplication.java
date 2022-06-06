package com.example.testmodel.springProxyFeatures;

import org.springframework.aop.framework.Advised;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Method;

@SpringBootApplication
public class TestSpringProxyApplication {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(TestSpringProxyApplication.class);

        Bean1 proxy  = context.getBean(Bean1.class);

        /**
         * 在spring容器中， 如果一个类有代理类的话，那么容器中只会存在代理类
         *
         *      手动调用的就是代理类的方法，肯定会被增强
         */
//        proxy.setBean2(new Bean2());
//        proxy.init();

        /**
         *  1. 演示 spring 代理的设计特点
         *
         *          依赖注入和初始化影响的初始对象
         *
         *          代理和目标是两个对象，二者成员并不共用数据
         */
//        showProxyAndTarget(proxy);


        /**
         *  2.  演示 static方法， final 方法  private方法 全都无法被增强
         */
        proxy.m1();
        proxy.m2();
        proxy.m3();
        Method m4 = Bean1.class.getDeclaredMethod("m4");
        m4.setAccessible(true);
        m4.invoke(proxy);

        context.close();

    }

    public static void showProxyAndTarget(Bean1 proxy) throws Exception{
        /**
         *  代理类由于是不参与 autowired 和 初始化阶段，所以他们的成员变量都是空的
         *  但是我们在 正常获取Bean1的时候里面能获取到Bean2， 是因为我们调用的是get方法，
         *  我们调用的所有代理方法，实际都是调用的院士对象的方法，所以我们可以获取到
         */


        System.out.println(">>>>>>>>>>>>  代理中的成员变量 ");
        System.out.println(" \t initialized =  " + proxy.initialized);
        System.out.println(" \t bean2 =  " + proxy.bean2);

        /**
         *  由于spring 容器中是没有 原始对象的，所以我们从代理对象中获取原始对象
         */
        if(proxy instanceof Advised){
            System.out.println(">>>>>>>>>>>>  目标中的成员变量 ");
            Advised advised = (Advised) proxy;
            Bean1 target = (Bean1) advised.getTargetSource().getTarget();
            System.out.println(target);
            System.out.println(" \t initialized =  " + target.initialized);
            System.out.println(" \t bean2 =  " + target.bean2);
        }
    }
}
