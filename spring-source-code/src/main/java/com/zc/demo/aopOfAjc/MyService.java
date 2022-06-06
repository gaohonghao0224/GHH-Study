package com.zc.demo.aopOfAjc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    private static final Logger log = LoggerFactory.getLogger(MyService.class);

    public  void foo(){
        System.err.println("foo ()");
        log.error("foo ()");
        foo2();
    }

    public void foo2(){
        System.err.println("foo2 ()");
    }
}
