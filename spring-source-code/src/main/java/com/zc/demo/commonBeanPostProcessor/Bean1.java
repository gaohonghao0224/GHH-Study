package com.zc.demo.commonBeanPostProcessor;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Data
public class Bean1 {
    private static final Logger log = LoggerFactory.getLogger(Bean1.class + "测试log");

    private Bean2 bean2;

    @Autowired
    public void setBean2(Bean2 bean2){
        System.out.println("@Autowired  生效   --- " + bean2);
        this.bean2 = bean2;
    }

    private Bean3 bean3;
    @Resource
    public void setBean3(Bean3 bean3){
        System.out.println("@Resource 生效  --- " + bean3);
        this.bean3 = bean3;
    }

    private String home;
    @Autowired
    public void setHome(@Value("${JAVA_HOME}") String home){
        System.out.println(" @Value 生效 ---  " + home);
        this.home = home;
    }

    public Bean1(){
        System.out.println(" 初始化方法");
        log.debug("初始化方法");
    }

    @PostConstruct
    public void init(){
        System.out.println("PostConstruct 生效，" );
    }

    @PreDestroy
    public void destory(){
        System.out.println(" predestory  销毁方法");
    }
}
