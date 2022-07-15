package com.example.springwebsocket.spring.controller;

import com.example.springwebsocket.spring.utils.RedisUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value ="/redis/")
public class ReisControllerTest {

    @GetMapping("get")
    public @ResponseBody Object get(String key){
        return RedisUtils.get(key);
    }

    @GetMapping("hmget")
    public @ResponseBody Object hmget(String key){
        return RedisUtils.hmget(key);
    }


}