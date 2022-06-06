package cn.itcast.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FanoutConfig {
    // 交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("itcast.fanout");
    }
    // 队列
    @Bean
    public Queue fanoutQueue1(){
        return new Queue("fanout.queue1");
    }

    @Bean
    public Queue fanoutQueue2(){
        return new Queue("fanout.queue2");
    }

    @Bean
    public Binding fanoutBinding1( Queue fanoutQueue1,FanoutExchange fanoutExchange){
        // 意思为： 把queue 绑定到 哪个交换机
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    /**
     *  多个 queue 只能分别绑定
     * @param fanoutQueue2
     * @param fanoutExchange
     * @return
     */
    @Bean
    public Binding fanoutBinding2( Queue fanoutQueue2,FanoutExchange fanoutExchange){
        // 意思为： 把queue 绑定到 哪个交换机
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

    /**
     *  传递 Object类型的 消息
     *
     *  创建一个队列
     */
    @Bean
    public Queue objectQueue(){
            return new Queue("object.queue");
    }


}
