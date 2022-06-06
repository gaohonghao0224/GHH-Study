package com.example.testmodel.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

import java.util.Map;

/**
 *  Condition 是 @Conditional注解的 核心接口
 *      接口返回的方法为 boolean类型，决定是否满足条件
 */
public class MyCondition1 implements Condition {
    /**
     *
     *  context: 包含 注册器， beanFactory，environment,rosourceLoader 等信息， 就是有各种容器各种配置的信息，借此来判断不同的判断逻辑
     * metadata  目标类的注解信息
     * @param context the condition context
     * @param metadata the metadata of the {@link org.springframework.core.type.AnnotationMetadata class}
     * or {@link org.springframework.core.type.MethodMetadata method} being checked
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        // TestConditionalOnClass 注解的信息
        Map<String, Object> attributes = metadata.getAnnotationAttributes(TestConditionalOnClass.class.getName());
        String className = attributes.get("className").toString();
        boolean exists = (boolean) attributes.get("exists");

        /**
         *  ClassUtils isPresent 方法判断是否有 对应的类
         */
        boolean present = ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource", null);

        return exists ? present : !present;
    }
}
