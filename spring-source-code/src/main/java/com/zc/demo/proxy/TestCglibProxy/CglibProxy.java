package com.zc.demo.proxy.TestCglibProxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy extends CglibTarget{

    // 扩展接口类，代理的业务接口
    private MethodInterceptor methodInterceptor;

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    static Method save0;
    static Method save1;
    static Method save2;

    static MethodProxy save0Proxy;
    static MethodProxy save1Proxy;
    static MethodProxy save2Proxy;

    static {
        try {
            save0 = CglibTarget.class.getMethod("save");
            save1 = CglibTarget.class.getMethod("save", int.class);
            save2 = CglibTarget.class.getMethod("save", long.class);

            /**
             *  猜测：
             *
             *       MethodProxy会创建出代理类的信息，根据参数描述先去调用代理类本身的增强方法，在去找对应的原始方法
              */
            save0Proxy = MethodProxy.create(CglibTarget.class,CglibProxy.class,"()V","save","saveSuper");
            save1Proxy = MethodProxy.create(CglibTarget.class,CglibProxy.class,"(I)V","save","saveSuper");
            save2Proxy = MethodProxy.create(CglibTarget.class,CglibProxy.class,"(J)V","save","saveSuper");

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    //  -------------------    原始功能方法  --------------------------------
    public void saveSuper() throws Throwable {
        super.save();
    }

    public void saveSuper(int i) throws Throwable{
        super.save(i);
    }

    public void saveSuper(long j) throws Throwable{
        super.save(j);
    }

    //  -------------------    增强功能方法  --------------------------------
    @Override
    public void save() throws Throwable {
        methodInterceptor.intercept(this,save0,new Object[0],save0Proxy);
    }

    @Override
    public void save(int i) throws Throwable {
        methodInterceptor.intercept(this,save1,new Object[]{3},save1Proxy);
    }

    @Override
    public void save(long j) throws Throwable {
        methodInterceptor.intercept(this,save2,new Object[]{j},save2Proxy);
    }
}
