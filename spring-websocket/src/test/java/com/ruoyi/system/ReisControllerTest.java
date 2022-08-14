package com.ruoyi.system;

import com.ruoyi.system.spring.utils.PropertiesReaderUtils;
import com.ruoyi.system.spring.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringWebsocketApplication.class)
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

  @Test
  void redisSet(){
    RedisUtils.set("测试",new String[]{"11","12"});
  }

}
