package cn.itcast.mq.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 测试提交
 *  测试笔记本提交  2
 */
@Component
public class SpringRabbitListener {

//    @RabbitListener(queues = "simple.queue")
//    public void listenSimpleQueue(String msg){
//        System.out.println("消费者接收到的 simple.queue的消息 ： 【   " + msg + "   】");
//    }

    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1接收到的 simple.queue的消息 ： 【   " + msg + "   】"+ LocalDateTime.now());
        Thread.sleep(20);
    }

    /**
     *  默认的消息分配为 消费预取机制， 事先平均分，你拿一个，我拿一个，分配之后消费者再去执行，
     *  具体的体现可以看到 拿到的消息分别为偶数和奇数
     *
     *  但是这不符合我们的要求，我们处理慢的应该是处理的少一些，因此我们需要设置一个
     *  spring.rabbitmq.listener.simple.prefetch:1的参数，  此参数的作用为 你每次只能获取几条消息，
     *  等这几条消费之后，你才能重新去获取，这样就可以根据性能去分配了
     */
    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue2(String msg) throws InterruptedException {
        System.err.println("消费者2........接收到的的消息 ： 【   " + msg + "   】" + LocalDateTime.now());
        Thread.sleep(200);

    }

    /**
     *  fanout Exchange 交换机
     *      Bean指定 Exchange Queue Binding 等信息
     * @param msg
     */
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg){
        System.out.println("消费者接收到的 fanout.queue1的消息 ： 【   " + msg + "   】");
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg){
        System.out.println("消费者接收到的 fanout.queue2的消息 ： 【   " + msg + "   】");
    }


    /**
     * Direct Exchange 交换机
     *  注解指定 Exchange Queue Binding 等信息
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("direct.queue1"),
            exchange = @Exchange(value = "itcast.direct",type = ExchangeTypes.DIRECT),
            key = {"blue","red"}

    ))
    public void listenDirectQueue1(String msg){
        System.out.println("消费者接受到 direct.queue1的消息 ： 【   " + msg + "   】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("direct.queue2"),
            exchange = @Exchange(value = "itcast.direct",type = ExchangeTypes.DIRECT),
            key = {"red","yellow"}

    ))
    public void listenDirectQueue2(String msg){
        System.out.println("消费者接受到 direct.queue2的消息 ： 【   " + msg + "   】");
    }


    /**
     * Topic Exchange 交换机
     *  注解指定 Exchange Queue Binding 等信息
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("topic.queue1"),
            exchange = @Exchange(value = "itcast.topic",type = ExchangeTypes.TOPIC),
            key = "china.#"

    ))
    public void listenTopicQueue1(String msg){
        System.out.println("消费者接受到 topic.queue1的消息 ： 【   " + msg + "   】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("topic.queue2"),
            exchange = @Exchange(value = "itcast.topic",type = ExchangeTypes.TOPIC),
            key = "#.news"

    ))
    public void listenTopicQueue2(String msg){
        System.out.println("消费者接受到 topic.queue2的消息 ： 【   " + msg + "   】");
    }

    /**
     *  接受 Object 的msg
     */
    @RabbitListener(queues = "object.queue")
    public void listenObjctQueue(Map<String,Integer> msg){
        System.out.println("接收到 Object.queue的消息    " + msg);
    }

}
