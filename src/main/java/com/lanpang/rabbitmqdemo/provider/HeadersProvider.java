package com.lanpang.rabbitmqdemo.provider;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;
import java.util.Map;

/**
 * @program: rabbitmqdemo
 * @description:
 * @author: yanghao
 * @create: 2018-10-22 10:12
 **/
@RestController
public class HeadersProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 单生产者-单消费者
     */
    @GetMapping("/headerstest")
    public void headerstest(){
        Map<String, Object> headers = new Hashtable<String, Object>();
        headers.put("name", "jack");
        headers.put("age", 30);
        String content = headers.toString();
        MessageProperties props = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).setMessageId("123").setHeader("age", "30")
                .build();
        Message message = MessageBuilder.withBody(content.getBytes()).andProperties(props).build();

        System.out.println("sender1 : " + headers.toString());
        this.rabbitTemplate.convertAndSend("headersExchange", "", message);
    }

}
