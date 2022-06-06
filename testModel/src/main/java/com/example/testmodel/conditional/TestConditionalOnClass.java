package com.example.testmodel.conditional;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(MyCondition1.class)
public @interface TestConditionalOnClass {
    //  判断是否存在
    boolean exists();

    // 要判断的类名
    String className();
}
