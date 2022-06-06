package com.zc.demo.proxy.TestJdkProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class A13 {
    /**
     *   总体来说 就是 代理模式 + 模板模式
     * @param args
     * @throws Throwable
     */

    public static void main(String[] args) throws Throwable {
        Foo proxy = new $Proxy0(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy,Method method,Object[] args) throws InvocationTargetException, IllegalAccessException {
                // 功能增强
                System.out.println(" proxy  before ...");
                // 调用目标
                Object result = method.invoke(new Target(), args);

                System.out.println(" proxy  after ...   " + (int)result);

                return result;
            }
        });
        proxy.foo();

        proxy.bar();
    }

}
