package com.zc.demo.springProxy;

import com.zc.demo.proxy.TestJdkProxy.Target;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 *      spring proxy 代理原理演示
 *
 *          1.spring 的代理选择规则
 *          2.底层的切点实现
 *          3.底层的通知实现
 *          4.ProxyFactory 使用创建代理的核心实现，用AopProxyFactory 选择具体代理实现
 *               - JdkDynamicAopProxy
 *               - ObjenesisCglibAopProxy
 */
public class SpringProxyOfPrinciple {

    /**
     *  切面的两种概念:
     *      1. Aspect:
     *              是代表一个通知类，里面可以有多个增强方法，以及对应的切点位置
     *                  通知1(advice) + 切点1（pointcut）
     *                  通知2(advice) + 切点2（pointcut）
     *                  通知3(advice) + 切点2（pointcut）
     *
     *      2.advisor:
     *              颗粒度更细的切面，代表 一个通知 + 一个切入点
     *              上述的 Aspect在运行时，也会被解析成多个 advisor
     *
     */

    public static void main(String[] args) {

        // 1.备好切点
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* foo())");

        //
        /**
         * 2.备好通知   ps：注意,要选aop的jar 
         *
         *      MethodInterceptor 是属于环绕通知
         */
        MethodInterceptor advice = invocation -> {
            System.out.println("before ...");
            Object proceed = invocation.proceed();
            System.out.println("after ...");
            return proceed;
        };

        // 3.备好切面
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut,advice);

        /**
         *   4.生成代理
         *
         *      选择代理规则：
         *          a. proxyTargetClass = false 时， 目标实现了接口，用JDK代理
         *          b. proxyTargetClass = false 时， 目标没有实现接口，使用Cglib代理
         *          c. proxyTargetClass = true 时，  总是使用Cglib代理
         */
//        Target1 target = new Target1();
        Target2 target = new Target2();
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(target);
        // 注入切面
        factory.addAdvisor(advisor);
        // 获得目标实现的接口，工厂才会知道你实现了接口，不然他不认为你实现了接口
        factory.setInterfaces(target.getClass().getInterfaces());
        factory.setProxyTargetClass(true);
        Target2 proxy = (Target2) factory.getProxy();

        System.out.println(proxy.getClass());
        proxy.foo();
        proxy.bar();


    }


    interface I1{
        void foo();
        void bar();
    };

    static class Target1 implements I1{
        @Override
        public void foo() {
            System.out.println("target1   foo ..");
        }

        @Override
        public void bar() {
            System.out.println("target1   bar ..");
        }
    }

    static class Target2{
        public void foo() {
            System.out.println("target2   foo ..");
        }

        public void bar() {
            System.out.println("target2   bar ..");
        }
    }


}
