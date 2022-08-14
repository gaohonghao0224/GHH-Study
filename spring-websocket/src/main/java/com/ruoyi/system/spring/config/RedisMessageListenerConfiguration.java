package com.ruoyi.system.spring.config;

import com.ruoyi.system.spring.listener.RedisDeleteListener;
import com.ruoyi.system.spring.listener.RedisExpiredListener;
import com.ruoyi.system.spring.listener.RedisUpdateAndAddListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * Redis  消息侦听器配置
 *
 * @author GHH
 * @date 2022/07/15
 */
@Configuration
public class RedisMessageListenerConfiguration {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUpdateAndAddListener redisUpdateAndAddListener;

    @Autowired
    private RedisDeleteListener redisDeleteListener;

    @Autowired
    private RedisExpiredListener redisExpiredListener;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //监听所有的key的set事件
        container.addMessageListener(redisUpdateAndAddListener, redisUpdateAndAddListener.getTopic());
        //监听所有key的删除事件
        container.addMessageListener(redisDeleteListener,redisDeleteListener.getTopic());
        //监听所有key的过期事件
        container.addMessageListener(redisExpiredListener,redisExpiredListener.getTopic());
        return container;
    }

}
