package com.zc.demo.pointCutMatch;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 *   切入点匹配底层练习
 */
public class PointCutMatchPrinciple {
    public static void main(String[] args) throws NoSuchMethodException {
        //  execution 切入点匹配
        AspectJExpressionPointcut executionPointcut = new AspectJExpressionPointcut();
        executionPointcut.setExpression("execution(* bar())");
        /**
         * public boolean matches(Method method, Class<?> targetClass)
         *      此方法回根据 切入点匹配信息 返回true false
         */
//        System.out.println(executionPointcut.matches(T1.class.getMethod("foo"),T1.class));
//        System.out.println(executionPointcut.matches(T1.class.getMethod("bar"),T1.class));


        // annotation 注解匹配 ， 只能匹配方法上面的注解
        AspectJExpressionPointcut annotationPointcut = new AspectJExpressionPointcut();
        annotationPointcut.setExpression("@annotation(org.springframework.transaction.annotation.Transactional)");
//        System.out.println(annotationPointcut.matches(T1.class.getMethod("foo"),T1.class));
//        System.out.println(annotationPointcut.matches(T1.class.getMethod("bar"),T1.class));

        // 方法和类匹配注解
        StaticMethodMatcherPointcut methodMatcherPointcut = new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                // 检查方法是否添加了 注解
                // MergedAnnotations spring检查注解的工具类
                MergedAnnotations annotation = MergedAnnotations.from(method);
                if(annotation.isPresent(Transactional.class)){
                    return true;
                }
                /**
                 *  查看目标类是否添加了 Transactional 注解
                 *      默认的规则为： 只查询本类的，不包含继承树，
                 *      TYPE_HIERARCHY 包含继承树
                 *
                 */
                annotation = MergedAnnotations.from(targetClass,MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
                if(annotation.isPresent(Transactional.class)){
                    return true;
                }
                return false;
            }
        };

        System.out.println(methodMatcherPointcut.matches(T1.class.getMethod("foo"), T1.class));
        System.out.println(methodMatcherPointcut.matches(T1.class.getMethod("bar"), T1.class));

        System.out.println(methodMatcherPointcut.matches(T2.class.getMethod("foo"), T2.class));
        System.out.println(methodMatcherPointcut.matches(T2.class.getMethod("bar"), T2.class));


        System.out.println(methodMatcherPointcut.matches(T3.class.getMethod("foo"), T3.class));
        System.out.println(methodMatcherPointcut.matches(T3.class.getMethod("bar"), T3.class));
    }

    static class T1{
        @Transactional
        public void foo(){

        }

        public void bar(){

        }

    }

    @Transactional
    static class T2{
        public void foo(){

        }
        public void bar(){

        }

    }

    @Transactional
    interface I3{
        public void foo();
        public void bar();
    }

    /**
     *  题外话：
     *      抽象类实现接口的时候，可以重写接口里面的方法，但是当去实例化这个抽象类的时候，就要去实现接口的方法！
     */

    static abstract class T3 implements I3{
        @Override
        public void foo() {

        }
    }

    static class T4 extends T3{
        @Override
        public void bar() {

        }

    }
}

