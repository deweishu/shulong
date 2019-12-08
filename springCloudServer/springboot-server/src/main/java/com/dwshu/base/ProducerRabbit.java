package com.dwshu.base;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author dwshu
 * @create 2019/12/8
 */
@Component
public class ProducerRabbit {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    /**
     * 用于direct直接模式
     *
     * @param routingKey 路由键
     * @param msg        消息
     */
    public void sendDirectMsg(String routingKey, String msg) {
        amqpTemplate.convertAndSend(routingKey, msg);
    }


    /**
     * 用于topic主题模式，fanout分裂模式
     *
     * @param exchange   交换机
     * @param routingKey 路由键
     * @param msg        消息
     */
    public void sendExchangeMsg(String exchange, String routingKey, String msg) {
        amqpTemplate.convertAndSend(exchange, routingKey, msg);
    }


    /**
     * 用于header头模式
     *
     * @param exchange 交换机
     * @param map      消息headers属性
     * @param msg      消息
     */
    public void sendHeaderMsg(String exchange, String msg, Map<String, Object> map) {
        amqpTemplate.convertAndSend(exchange, null, msg, message -> {
            message.getMessageProperties().getHeaders().putAll(map);
            return message;
        });
    }


}
