package com.example.springwebsocket;

import com.example.springwebsocket.spring.utils.RedisUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value ="/redis/")
public class ReisControllerTest {

    @GetMapping("/get")
    public @ResponseBody Object get(){
        return RedisUtils.get("ghh");
    }

}
