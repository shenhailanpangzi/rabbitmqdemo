package com.lanpang.rabbitmqdemo.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: rabbitmqdemo
 * @description:
 * @author: yanghao
 * @create: 2018-10-22 10:12
 **/
@RestController
public class TopicProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     *
     */
    @GetMapping("/topictest")
    public void topictest(){
        System.out.println("Sending message>>>");
        Object message = "~~我是消息hello~~";
        amqpTemplate.convertAndSend("TOPIC-EXCHANGE", "topic.11.hello", message);
    }
}
