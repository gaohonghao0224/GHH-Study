package com.example.springwebsocket.spring.listener;

import lombok.Data;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.stereotype.Component;

/**
 *  监听 redis key 过期事件
 *
 *  PatternTopic 的规则可以看 RedisUpdateAndAddListener类注释
 *  @see RedisUpdateAndAddListener
 *
 * @author GHH
 * @date 2022/07/15
 */
@Data
@Component
public class RedisExpiredListener implements MessageListener {

    //监听主题
    private  final PatternTopic topic = new PatternTopic("__keyevent@*__:expired");

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String topic = new String(pattern);
        String msg = new String(message.getBody());
        System.out.println("收到key的过期，消息主题是："+ topic+",消息内容是："+msg);
    }
}