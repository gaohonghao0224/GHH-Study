package com.zc.demo.proxy.TestJdkProxy;

/**
 *   目标类
 */
public class Target implements Foo{
    @Override
    public int foo() {
        System.out.println(" Target foo run ...");
        return 100;
    }

    @Override
    public int bar() {
        System.out.println(" Target bar run ...");
        return 200;
    }
}
