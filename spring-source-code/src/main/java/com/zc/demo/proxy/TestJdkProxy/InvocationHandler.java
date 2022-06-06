package com.zc.demo.proxy.TestJdkProxy;

import java.lang.reflect.Method;

/**
 * 此接口的作用为 具体的代理代码解耦
  */
public interface InvocationHandler {
    /**
     *  分辨代理的方法，解耦
     * @param method    从具体的接口中获取对应的方法，然后使用 Method 在启动类中做反射调用，寻找对应的实例
     * @param args
     * @return
     * @throws Throwable
     */
    Object invoke(Object proxy,Method method, Object[] args) throws Throwable;
}
