package com.zc.demo.scope;

import com.zc.demo.scope.invalid.E;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class scopeApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(scopeApplication.class, args);

//        // 单例，获取的Bean实例始终为同一个
//        System.err.println(" singleton   " + context.getBean(BeanForSingleton.class));
//        System.err.println(" singleton   " + context.getBean(BeanForSingleton.class));
//
//        // 每次返回新的实例
//        // 这个没有销毁，那么我猜就是 同名或者同类型的，把前面的给重置掉了
//        System.err.println(" Prototype   " + context.getBean(BeanForPrototype.class));
//        System.err.println(" Prototype   " + context.getBean(BeanForPrototype.class));
//        System.err.println(" Prototype   " + context.getBean(BeanForPrototype.class));
//
//        String[] prototypes = context.getBeanNamesForType(BeanForPrototype.class);
//        for(String p : prototypes) System.out.println(p);

        E e = context.getBean(E.class);
        System.err.println("f1   " + e.getF1().getClass());
        System.err.println("f1   " +e.getF1());
        System.out.println("  =============  ");
        System.err.println("f2   " +e.getF2().getClass());
        System.err.println("f2   " +e.getF2());

        System.err.println("f3   " +e.getF3().getClass());
        System.err.println("f3   " +e.getF3());

        System.err.println("f4   " +e.getF4().getClass());
        System.err.println("f4   " +e.getF4());
    }
}
