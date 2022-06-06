package com.zc.demo.proxy.TestCglibProxy;

/**
 *  Cglib 目标类
 */
public class CglibTarget {

    public void save() throws Throwable {
        System.out.println("save  ..");
    }

    public void save(int i) throws Throwable {
        System.out.println("save1  ..");
    }

    public void save(long j) throws Throwable {
        System.out.println("save2  ..");
    }
}
