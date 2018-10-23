package com.lanpang.rabbitmqdemo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: rabbitmqdemo
 * @description: 直连消费者
 * @author: yanghao
 * @create: 2018-10-22 09:41
 **/
@Component
public class TopicConsumer {

    @Component
    public class TopicConsumer1 {

        @RabbitListener(queues = "topic.Queue1")
        public void receivePointMessage(Object message) {
            System.out.println("TopicConsumer1接收到来自Queue1的信息 <" + message + ">");
        }
    }

    @Component
    public class TopicConsumer2 {

        @RabbitListener(queues = "topic.Queue2")
        public void receivePointMessage(Object message) {
            System.out.println("TopicConsumer2接收到来自Queue2的信息 <" + message + ">");
        }
    }

    @Component
    public class TopicConsumer3 {

        @RabbitListener(queues = "topic.Queue3.hello")
        public void receivePointMessage(Object message) {
            System.out.println("TopicConsumer3接收到来自Queue3的信息 <" + message + ">");
        }
    }
}
