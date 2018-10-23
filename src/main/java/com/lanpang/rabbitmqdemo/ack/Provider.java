package com.lanpang.rabbitmqdemo.ack;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @program:
 * 1、消息发送确认
 * ConfirmCallback  只确认消息是否正确到达 Exchange 中。
 * ReturnCallback   消息没有正确到达队列时触发回调，如果正确到达队列不执行。
 * 2、消息接收确认
 * 默认情况下消息消费者是自动 ack （确认）消息的，需要设置为手动确认，原因是：自动确认会在消息发送给消费者后立即确认，这样存在丢失消息的可能。
 * @description:
 * @author: yanghao
 * @create: 2018-10-22 10:12
 **/
@RestController
public class Provider implements RabbitTemplate.ConfirmCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 单生产者-单消费者
     */
    @GetMapping("/provider")
    public void provider(){
        System.out.println("Sending message>>>");
        //设置回调类
        rabbitTemplate.setConfirmCallback(this);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        Object message = "发给Queue的消息~";
        rabbitTemplate.convertAndSend("ACK.EXCHANGE", "consumer_queue", message,correlationData);
    }

    //当生产者发布消息到RabbitMQ中，生产者需要知道是否真的已经发送到RabbitMQ中，需要RabbitMQ告诉生产者。
    //    Confirm机制
    //channel.confirmSelect();
    //channel.waitForConfirms();
    //
    //    事务机制
    //channel.txSelect();
    //channel.txRollback();
//    注意：事务机制是非常非常非常消耗性能的，最好使用Confirm机制，Confirm机制相比事务机制性能上要好很多。
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息唯一标识："+correlationData);
        System.out.println("确认结果："+ack);
        System.out.println("失败原因："+cause);
    }
}
