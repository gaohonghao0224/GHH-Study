package cn.itcast.mq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableRabbit
public class SpringAMQPTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessageSimpleQueue(){
        String queueName = "simple.queue";
        String message = "hello, spring amqp2";
        rabbitTemplate.convertAndSend(queueName,message);
    }

    @Test
    public void testSendMessageWorkQueue() throws InterruptedException {
        String queueName = "simple.queue";
        String message = "hello, spring amqp2";
        for (int i = 0; i < 50; i++) {
            rabbitTemplate.convertAndSend(queueName,message + "___" + i);
            Thread.sleep(20);
        }
    }

    @Test
    public void testSendMessageFanoutExchange() throws InterruptedException {
        // 交换机名称
        String exchangeName = "itcast.fanout";
        // 消息
        String  message = " hello, every one";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName,"",message);
    }



    @Test
    public void testSendMessageDirectExchange() throws InterruptedException {
        // 交换机名称
        String exchangeName = "itcast.direct";
        // 消息
        String  message = " hello, yellow";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName,"yellow",message);
    }


    @Test
    public void testSendMessageTopicExchange() throws InterruptedException {
        // 交换机名称
        String exchangeName = "itcast.topic";
        // 消息
        String  message = " ! 黑马叫传智教育吗？";
        message = "我喜欢下雨";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName,"china.tianqi",message);
    }

    /**
     *  发送  object 类型的消息
     */
    @Test
    public void sendObjectMsg(){
        Map<String,Integer> msg = new HashMap<>();
        msg.put("柳岩",21);
        rabbitTemplate.convertAndSend("object.queue",msg);
    }
}
