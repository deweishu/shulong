package com.itheima.rabbit;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"itheima"})
public class Customer02 {

    @RabbitHandler
    public void showMessage(String message){
        System.out.println("itheima 收到了消息=" + message);
    }


}
