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
public class FanoutProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 单生产者-单消费者
     */
    @GetMapping("/fanouttest")
    public void fanouttest(){
        System.out.println("Sending message>>>");
        Object message = "fanouttest给全体发的消息~";
//        amqpTemplate.convertAndSend("Queue1",message1);
        amqpTemplate.convertAndSend("FANOUTEX-CHANGE", "aaa", message);
    }
}
