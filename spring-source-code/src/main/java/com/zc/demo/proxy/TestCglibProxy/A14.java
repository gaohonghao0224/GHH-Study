package com.zc.demo.proxy.TestCglibProxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class A14 {
    public static void main(String[] args) throws Throwable {
        CglibTarget cglibTarget = new CglibTarget();
        CglibProxy proxy = new CglibProxy();
        proxy.setMethodInterceptor(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println(" before () ....");
//                return method.invoke(cglibTarget,objects);

                // 内部无反射
//                return methodProxy.invoke(cglibTarget, objects);

                // 内部无反射，且不需要目标类实例
                return methodProxy.invokeSuper(o, objects);
            }
        });

        proxy.save();
        proxy.save(1);
        proxy.save(10l);
    }
}
