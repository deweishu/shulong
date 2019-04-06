package com.itheima.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 */
@Component
@RabbitListener(queues = {"itcast"}) //queues属性: 队列的名字
public class Customer01 {

    @RabbitHandler
    //处理消息
    public void showMessage(String message){
        System.out.println("itcast 收到了消息=" + message);
    }

}
