package com.zc.demo.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 *  会话作用域
 *      两种概念：
 *          不同的浏览器在服务端为不同的 session
 *          一个session 在某一个固定时间内
 */
@Scope("session")
@Component
public class BeanForSession {
    @PreDestroy
    public void destroy(){
        System.err.println(" BeanForSession  销毁");
    }
}
