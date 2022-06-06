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
 *              然后在生成 代理类的代理类， 调用 invoke 方法， 然后根据创建的MethodProxy 获取对应的方法， 在根据传递过来的 目标类参数执行对应的方法 来实现非反射调用
 *
 *      模仿 MethodProxy 生成的  目标类代理
 *
 */
public class TargetFastClass {

    /**
     *  模仿 根据 调用方 MethodProxy.create 生成的方法签名
     */
    static Signature s0 = new Signature("save","()V");
    static Signature s1 = new Signature("save","(I)V");
    static Signature s2 = new Signature("save","(J)V");

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
     * @param target
     * @param args
     */
    public Object invoke(int index,Object target,Object[] args) throws Throwable {
        if (index == 0){
            ((CglibTarget)target).save();
        }else if (index == 1){
            ((CglibTarget)target).save((int) args[0]);
        }else if (index == 2){
            ((CglibTarget)target).save((long) args[0]);
        }

        return null;
    }

    public static void main(String[] args) throws Throwable {
        // 测试
        TargetFastClass fastClass = new TargetFastClass();

        fastClass.invoke( fastClass.getIndex(new Signature("save","()V")),new CglibTarget(),new Object[0]);

    }

}
