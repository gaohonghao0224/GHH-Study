package com.zc.demo.proxy.TestJdkProxy;

import java.lang.reflect.Method;

/**
 * 代理类
 */
public class $Proxy0 implements Foo {

    /**
     *  将 method值静态化，因为方法是固定的，只需要获取一次就可以了
     */
    static Method foo;
    static Method bar;

    static {
        try {
            foo = Foo.class.getMethod("foo");
            bar = Foo.class.getMethod("bar");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public $Proxy0() {}

    public $Proxy0(InvocationHandler handler) {
        this.handler = handler;
    }

    /**
     *  用于接受具体的代理内容
     *  说白了，接受一个此接口的实现类
     */
    private InvocationHandler handler;

    @Override
    public int foo() throws Throwable {
        Object result = handler.invoke(this,foo, new Object[0]);
        return (int) result;
    }

    @Override
    public int bar() throws Throwable {
        Object result = handler.invoke(this,bar, new Object[0]);
        return (int) result;
    }

}


