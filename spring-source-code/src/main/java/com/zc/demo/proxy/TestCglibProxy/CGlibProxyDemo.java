package com.zc.demo.proxy.TestCglibProxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

/**
 *  Cglib 代理
 */
public class CGlibProxyDemo {
    static class Target{
        public void foo(){
            System.out.println(" target  foo()1 ...");
        }
    }

    public static void main(String[] args) {
        Target target = new Target();
        // 代理是子类型，目标类是父类型
        Target proxy = (Target) Enhancer.create(Target.class, new MethodInterceptor() {
            @Override
            /**
             *  Object o  :  代理对象
             *  Method  method : 目标方法
             *  objects ： 参数列表
             *  methodProxy ： 另一种代理对象
             */
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println(" before ...");
                // 使用反射调用目标
//                method.invoke(target,objects);

                //  和上面的 invoke是两个方法，此方法中没有使用代理
                // spring的Cglib代理 默认使用这种方法
//                methodProxy.invoke(target,objects);

                // 无需实例，使用属性 参数 Object o 调用,内部也没有使用反射
                methodProxy.invokeSuper(o,objects);
                System.out.println(" after  ...");
                return null;
            }
        });

        proxy.foo();
    }
}
