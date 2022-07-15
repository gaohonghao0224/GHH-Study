package com.example.springwebsocket.spring.listener;

import com.example.springwebsocket.spring.handler.WebSocketHandler;
import com.example.springwebsocket.spring.utils.RedisUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 监听 redis key改变事件
 *
 * // todo 此类的监听器可以为多个，看实际数据之后，再决定是否要添加多个
 * @author GHH
 * @date 2022/07/15
 */
@Component
@Data
public class RedisUpdateAndAddListener implements MessageListener {
    /**
     *  规则
     *       psubscribe '__key*__:*'#对所有库键空间通知
     *
     *       psubscribe '__keyspace@2__:*' #是对db2数据库键空间通知
     *
     *       psubscribe '__keyspace@2__:order*' #是对db2数据库，key前缀为order所有键的键空间通知
     *
     */

    @Autowired
    private WebSocketHandler webSocketHandler;

    private static final String KEY_PREFIX = "tcp*";
    private static final String KEY_SPACE = "__keyspace@1__:";

    // 监听 index[1] 的数据库中 KEY_PREFIX 开头的key
    private static final String KEY_SPACE_PREFIX = KEY_SPACE + KEY_PREFIX ;

    // 监听的主题
    private final PatternTopic topic = new PatternTopic(KEY_SPACE_PREFIX);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String topic = new String(pattern);
        String msg = new String(message.getBody());
        String redisKey = StringUtils.stripStart(new String(message.getChannel()), KEY_SPACE);

        // 如果不是 hash格式数据，就不监听
        if(!msg.equals("hset")) return ;

        Map<Object, Object> value = new HashMap<>();
        value = RedisUtils.hmget(redisKey);
        // 不包含这两个key，说明数据格式有问题
        if (!(value.containsKey("keyType") && value.containsKey("data"))) return ;

        webSocketHandler.sendMessage(value.get("keyType").toString(),value.get("data").toString());

        System.out.println("收到key更新或修改，消息主题是："+ topic+",消息内容是："+msg );
        System.out.println("更改的key为："+ redisKey+",value内容是："+ value);
    }


}
