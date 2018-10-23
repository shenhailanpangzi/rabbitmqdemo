package com.lanpang.rabbitmqdemo.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
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
public class DirectProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 单生产者-单消费者
     */
    @GetMapping("/oneTone")
    public void oneTone(){
        System.out.println("Sending message>>>");
        Object message = "发给Queue1的消息~";
//        amqpTemplate.convertAndSend("Queue1",message1);
        amqpTemplate.convertAndSend("DIRECT-DIRECT", "Queue1", message);
    }

    /**
     * 单生产者-多消费者
     * RabbitMQ默认的消息机制其实是轮询分发，只是spring-boot中结合的RabbitMQ封装的，采用了公平分发机制。所以在这里采用的消息机制是公平分发。
     */
    @GetMapping("/oneToMany")
    public void oneToMany(){
        System.out.println("Sending message>>>");
        for (int i = 0; i < 9; i++) {
            amqpTemplate.convertAndSend("DIRECT-DIRECT", "Queue2", "第[" + (i + 1) + "]个 ---------> ");
        }
    }

    /**
     * 多生产者-多消费者
     */
    @GetMapping("/manyToMany")
    public void manyToMany(){
        System.out.println("Sending message>>>");
        for (int i = 0; i < 9; i++) {
            amqpTemplate.convertAndSend("DIRECT-DIRECT", "Queue1", "第[" + (i + 1) + "]个 ---------> ");
            amqpTemplate.convertAndSend("DIRECT-DIRECT", "Queue2", "第[" + (i + 1) + "]个 ---------> ");
        }
    }

}
