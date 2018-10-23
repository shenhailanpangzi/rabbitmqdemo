package com.lanpang.rabbitmqdemo.ack;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @program: rabbitmqdemo
 * @description:
 * @RabbitListener 可以标注在类上面，需配合 @RabbitHandler 注解一起使用
 * @RabbitListener 标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，具体使用哪个方法处理，根据 MessageConverter 转换后的参数类型
 * @author: yanghao
 * @create: 2018-10-22 09:41
 **/
@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = "ACK.EXCHANGE",durable = "false",type = "topic",autoDelete = "true"),
        value = @Queue(value = "consumer.queue",durable = "false",autoDelete = "true"),
        key = "consumer.queue"
))
public class Consumer {

    @RabbitHandler
    public void processMessage(Channel channel, Message message) throws IOException {
        System.out.println("监听到消息:"+message.getBody());
        if (new String(message.getBody()).contains("Queue1")){
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("receiver success");
        }else {
            //ack返回false，是否重新回到队列true
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
            System.out.println("receiver fail");
        }
    }
}