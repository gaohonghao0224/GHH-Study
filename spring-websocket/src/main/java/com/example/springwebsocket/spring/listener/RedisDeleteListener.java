package com.example.springwebsocket.spring.listener;

import lombok.Data;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.stereotype.Component;

/**
 * 监听 redis key 删除事件
 *
 *  PatternTopic 的规则可以看 RedisUpdateAndAddListener类注释
 *  @see RedisUpdateAndAddListener
 *
 * @author GHH
 * @date 2022/07/15
 */

@Component
@Data
public class RedisDeleteListener implements MessageListener {
    //监听主题
    private  final PatternTopic topic = new PatternTopic("__keyevent@*__:del");

    /**
     *
     * @param message 消息
     * @param pattern 主题
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String topic = new String(pattern);
        String msg = new String(message.getBody());
        System.out.println("收到key的删除，消息主题是："+ topic+",消息内容是："+msg);
    }

}
