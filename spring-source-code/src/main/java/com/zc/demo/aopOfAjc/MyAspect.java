package com.zc.demo.aopOfAjc;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
//@Component
public class MyAspect {
    private static final Logger log = LoggerFactory.getLogger(MyAspect.class);

    @Before("execution(* com.zc.demo.aopOfAjc.MyService..*(..))")
    public void before(){
        System.err.println("before()   ..");
        log.error("before()   ..");
    }
}
