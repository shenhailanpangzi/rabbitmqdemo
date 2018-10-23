package com.lanpang.rabbitmqdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.DigestException;

/**
 * @program: rabbitmqdemo
 * @description:
 * @author: yanghao
 * @create: 2018-10-21 21:17
 **/
@Component
public class RabbitConfig {

    /**
     * 创建三种交换机
     * Name
     * Durability （消息代理重启后，交换机是否还存在）
     * Auto-delete （当所有与之绑定的消息队列都完成了对此交换机的使用后，删掉它）
     * Arguments（依赖代理本身）
     */
//    1、Direct Exchange（直连交换机） 根据消息携带的路由键（routing key）将消息投递给对应队列的
    @Bean("directExchange")
    public DirectExchange directExchange(){
        return new DirectExchange("DIRECT-DIRECT",false,true);
    }
//    2、Topic Exchange（主题交换机）  通过对消息的路由键和队列到交换机的绑定模式之间的匹配，将消息路由给一个或多个队列。
    @Bean("topicExchange")
    public TopicExchange topicExchange(){
        return new TopicExchange("TOPIC-EXCHANGE",false,true);
    }
//    3、Fanout Exchange（扇型交换机） 将消息路由给绑定到它身上的所有队列，而不理会绑定的路由键
    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("FANOUTEX-CHANGE",false,true);
    }
//    4、Headers Exchange（头交换机）有时消息的路由操作会涉及到多个属性，此时使用消息头就比用路由键更容易表达，头交换机（headers exchange）就是为此而生的。
//    @Bean("headersExchange")
//    public HeadersExchange  headersExchange(){
//        return new HeadersExchange("HEADERSEX-CHANGE",false,true);
//    }

    /**
     * 1、创建Direct Exchange队列
     */
    @Bean
    public Queue directQueue1() {
        return new Queue("Queue1",false,true,true);
    }
    @Bean
    public Queue directQueue2() {
        return new Queue("Queue2",false,true,true);
    }
    /**
     * 绑定三种交换机和队列
     */
//    1、绑定Direct Exchange（直连交换机）
    @Bean
    Binding bindingExchangeQueue1(@Qualifier("directQueue1") Queue queueMessage, DirectExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("Queue1");
    }

    @Bean
    Binding bindingExchangeQueue2(@Qualifier("directQueue2")Queue queueMessage, DirectExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("Queue2");
    }



    /**
     * 2、创建topic Exchange队列
     * Name
     * Durable（消息代理重启后，队列依旧存在）
     * Exclusive（只被一个连接（connection）使用，而且当连接关闭后队列即被删除）
     * Auto-delete（当最后一个消费者退订后即被删除）
     * Arguments（一些消息代理用他来完成类似与TTL的某些额外功能）
     */
    @Bean
    public Queue topicQueue1() {
        return new Queue("topic.Queue1",false,true,true);
    }
    @Bean
    public Queue topicQueue2() {
        return new Queue("topic.Queue2",false,true,true);
    }
    @Bean
    public Queue topicQueue3() {
        return new Queue("topic.Queue3.hello",false,true,true);
    }
//    2、绑定topic Exchange（主题交换机）
    @Bean
    Binding bindingExchangeMessages1(@Qualifier("topicQueue1")Queue queueMessages, @Qualifier("topicExchange")TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.Queue1");
    }
    @Bean
    Binding bindingExchangeMessages2(@Qualifier("topicQueue2")Queue queueMessages,@Qualifier("topicExchange") TopicExchange exchange) {
//        只要符合 ("topic.#") 规则就将此消息发送给topicQueue2队列
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");//*表示一个词,#表示零个或多个词
    }
    @Bean
    Binding bindingExchangeMessages3(@Qualifier("topicQueue3")Queue queueMessages,@Qualifier("topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.*.hello");//*表示一个词,#表示零个或多个词
    }

//    3、绑定Fanout Exchange（扇型交换机） 将消息路由给绑定到它身上的所有队列
    @Bean
    Binding bindingExchangeA(@Qualifier("directQueue1")Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeB(@Qualifier("directQueue2")Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    // ===============以下是验证Headers Exchange的队列和交互机==========

//    @Bean
//    public Queue headersQueueA() {
//        return new Queue("headers.A",false,false,true);
//    }
//    @Bean
//    Binding bindingHeadersExchangeA(@Qualifier("headersQueueA") Queue headersQueueA, HeadersExchange headersExchange) {
//        // 这里x-match有两种类型
//        // all:表示所有的键值对都匹配才能接受到消息
//        // any:表示只要有键值对匹配就能接受到消息
//        return BindingBuilder.bind(headersQueueA).to(headersExchange).where("age").exists();
//    }

    // ===============以上是验证Headers Exchange的队列和交互机==========
}
