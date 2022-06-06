package com.zc.demo.scope.controller;

import com.zc.demo.scope.BeanForRequest;
import com.zc.demo.scope.BeanForSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class controller {

    @Lazy
    @Autowired
    private BeanForRequest beanForRequest;

    @Lazy
    @Autowired
    private BeanForSession beanForSession;

    @RequestMapping("testScope")
    @ResponseBody
    public String testScope(){
        return "beanForRequest" + beanForRequest + "</br>"
                + "beanForSession" + beanForSession;
    }

}
