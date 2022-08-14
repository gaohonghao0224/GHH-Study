package com.ruoyi.system.spring.controller;

import com.ruoyi.system.monitorByWebSocket.model.ConverterModel;
import com.ruoyi.system.spring.entity.User;
import com.ruoyi.system.spring.utils.RedisUtils;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value ="/redis/")
public class ReisControllerTest {

    @GetMapping("get")
    public  Object get(String key){
        return RedisUtils.get(key);
    }

    @GetMapping("hmset")
    public  Object hmset(String key){
        Map<String, Object> result = new HashMap<>();
        User user = new User("ghh2",12);
        result.put(user.getName(),user);
        return RedisUtils.hmset("hset:entity:2",result);
    }

    @GetMapping("hmget")
    public  Object hmget(String key){
        return RedisUtils.hmget(key);
    }

    @GetMapping("testSet")
    public Object testSet(){
       return  RedisUtils.sSet("aa","bb");
    }

    @GetMapping("getRedisSet")
    public Object getRedisSet(){
      return   RedisUtils.sGet("aa");
    }

    @SneakyThrows
    @GetMapping("setblq")
    public void setblq() {
        Long i = 0l;
        while (i<100000) {
            Thread.sleep(25);
            i++;
            System.out.println(i);
            String key = "tcp" + ":" + "tractionConverterSystem" + ":" + "converterChargingCondition";
            ConverterModel c1 = new ConverterModel("变流器1", String.valueOf(i),  String.valueOf(i),  String.valueOf(i),  String.valueOf(i),  String.valueOf(i),  String.valueOf(i),1);
            RedisUtils.hset(key, "converter1#", c1);
        }
    }




}