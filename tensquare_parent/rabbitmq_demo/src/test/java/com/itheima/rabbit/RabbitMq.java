package com.itheima.rabbit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//SpringBoot里面整合单元测试
@SpringBootTest(classes = RabbitMqApplication.class) //指定入口
@RunWith(SpringRunner.class) //指定运行环境
public class RabbitMq {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    //发送直接模式（Direct）消息
    public void sendDirect(){
        rabbitTemplate.convertAndSend("itcast","Hello...直接模式（Direct）");
    }


    @Test
    //发送分列模式(Fanout)消息
    public void sendFanout(){
        rabbitTemplate.convertAndSend("exchange.fanout","","Hello...分列模式（Fanout）");
    }

    @Test
    //发送主题模式(Topic)消息
    public void sendTopc01(){
        rabbitTemplate.convertAndSend("exchange.topic","goods.aa","Hello...主题模式（Topic）");
    }

    @Test
    //发送主题模式(Topic)消息
    public void sendTopc02(){
        rabbitTemplate.convertAndSend("exchange.topic","aa.log","Hello...主题模式（Topic）");
    }

    @Test
    //发送主题模式(Topic)消息
    public void sendTopc03(){
        rabbitTemplate.convertAndSend("exchange.topic","goods.log","Hello...主题模式（Topic）");
    }


}
