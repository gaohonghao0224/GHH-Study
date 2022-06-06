package com.example.testmodel.springProxyFeatures;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {
    //  对所有方法进行增强
    @Before("execution(* com.example.testmodel.springProxyFeatures.Bean1.*(..))")
    public void before(){
        System.out.println("before ...");
    }
}
