package com.zc.demo.proxy.TestMethodProxy;


import com.zc.demo.proxy.TestCglibProxy.CglibProxy;
import com.zc.demo.proxy.TestCglibProxy.CglibTarget;
import org.springframework.cglib.core.Signature;

/**
 *  在 CglibProxy 类中 在静态创建 MethodProxy 时，就会创建此代理类的代理类， 是字节码创建的，我们这里是模仿
 *
 *
 *
 *      原理：
 *           是根据 MethodProxy.create(CglibTarget.class,CglibProxy.class,"()V","save","saveSuper"); 创建的时候
 *           就能获取我们要代理的方法的签名，在有 getIndex 获取对应方法的编号， 在实际调用 invoke的时候，就鞥根据对应的编号获取对应的方法，
 *           在有 目标类参数 target 来实际调用，就避免了反射。
 *
 *           我觉得就是一个很简单的调用转移，静态代理吧属于是。
 *
 *      流程为 ：  启动类  调用代理类的 save方法 -- 生成代理类执行save增强，也就是代理的具体逻辑  -- 代理逻辑会根据 MethodProxy 参数会调用 invoke方法  --
 *              然后在生成 代理类的代理类， 调用 invoke 方法， 然后根据创建的MethodProxy 获取对应的方法， 在根据传递过来的 第一层代理类 参数执行对应的方法 来实现非反射调用
 *
 *
 *              不同的是，这个重点是这个第一层代理类，这个类就是最初的代理类，这个类里面不仅实现了最初的增强方法，也实现了 目标类（父类）的功能方法，使用 super调用的
 *              所以要获取 saveSuper 的方法签名，以此来调用父类的方法，因为此实现方法 saveSuper 就是在 MethodProxy生成的代理类中，所以直接调用就可以了，也就是说只需要
 *              第一层代理类对象就可以了，不需要目标类对象了
 *              理解： 就是把第二层代理类把父类的方法全都实现了，就完全用不到父类了， 第一层代理类又是增强类，又是父类。
 *
 *
 *
 *      模仿 MethodProxy 生成的  目标类代理
 *
 */
public class ProxyFastClass {

    /**
     *  模仿 根据 调用方 MethodProxy.create 生成的方法签名
     */
    static Signature s0 = new Signature("saveSuper","()V");
    static Signature s1 = new Signature("saveSuper","(I)V");
    static Signature s2 = new Signature("saveSuper","(J)V");

    /**
     *  根据方法签名，获取目标方法的编号
     * @param signature
     */
    public int getIndex(Signature signature){
        if(s0.equals(signature)){
            return 0;
        }else if(s1.equals(signature)){
            return 1;
        }else if(s2.equals(signature)){
            return 2;
        }
        return -1;
    }

    /**
     *   根据方法编号，调用目标方法
     * @param index
     * @param proxy
     * @param args
     */
    public Object invoke(int index,Object proxy,Object[] args) throws Throwable {
        if (index == 0){
            ((CglibProxy)proxy).save();
        }else if (index == 1){
            ((CglibProxy)proxy).save((int) args[0]);
        }else if (index == 2){
            ((CglibProxy)proxy).save((long) args[0]);
        }

        return null;
    }

    public static void main(String[] args) throws Throwable {
        // 测试
        ProxyFastClass fastClass = new ProxyFastClass();

        fastClass.invoke( fastClass.getIndex(new Signature("save","()V")),new CglibProxy(),new Object[0]);

    }

}
