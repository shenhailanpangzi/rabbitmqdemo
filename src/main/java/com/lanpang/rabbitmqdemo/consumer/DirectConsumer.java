package com.lanpang.rabbitmqdemo.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @program: rabbitmqdemo
 * @description: 直连消费者
 * @author: yanghao
 * @create: 2018-10-22 09:41
 **/
@Component
public class DirectConsumer {

    @Component
    public class DirectConsumer1 {

        @RabbitListener(queues = "Queue1")
        public void receivePointMessage(Message message, Channel channel){
            System.out.println("DirectConsumer1接收到来自Queue1的信息 <" + message.getBody() + ">");
        }
    }

    @Component
    public class DirectConsumer2 {

        @RabbitListener(queues = "Queue2")
        public void receivePointMessage(Object message) {
            System.out.println("DirectConsumer2接收到来自Queue2的信息 <" + message + ">");
        }
    }


//    //    为消费者配置监听器
//    @Bean
//    MessageListenerAdapter Queue1ListenerAdapter(DirectConsumer1 receiver) {
//        return new MessageListenerAdapter(receiver, "Queue1");
//    }
//
//    @Bean
//    MessageListenerAdapter Queue2ListenerAdapter(DirectConsumer2 receiver) {
//        return new MessageListenerAdapter(receiver, "Queue2");
//    }
//
////    将监听器配置到 Container 中
//    @Autowired
//    private ConnectionFactory connectionFactory;
//    @Bean
//    SimpleMessageListenerContainer pointContainer(MessageListenerAdapter Queue1ListenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames("Queue1");
//        container.setMessageListener(Queue1ListenerAdapter);
//        return container;
//    }
//
//    @Bean
//    SimpleMessageListenerContainer performanceContainer(MessageListenerAdapter Queue2ListenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames("Queue2");
//        container.setMessageListener(Queue2ListenerAdapter);
//        return container;
//    }
}
