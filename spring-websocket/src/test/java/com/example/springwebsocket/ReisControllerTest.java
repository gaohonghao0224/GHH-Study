package com.example.springwebsocket;

import com.example.springwebsocket.spring.utils.PropertiesReaderUtils;
import com.example.springwebsocket.spring.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReisControllerTest {

  @Test
    public void demo1(){
      System.out.println(StringUtils.stripStart("gaohonghao", "gao"));
      System.out.println(StringUtils.stripStart("starfish","star"));
  }

  @Test
  void testProperties() throws Exception{
      PropertiesReaderUtils.readProperties(ReisControllerTest.class.getClassLoader().getResource("redisKeyRelation.properties").getPath());
      String keys = PropertiesReaderUtils.getKey("webSocket");

    List<String> list = Arrays.asList(keys.split(","));

    System.out.println(list.toString());
  }

}
