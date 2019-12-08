package com.dwshu.base;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author dwshu
 * @create 2019/12/8
 */
@Component
public class ConsumerRabbit {

    @RabbitListener(queues = "myQueue")
    public void processMessage(String msg){
        System.out.format("Receiving Message: -----[%s]----- \n.", msg);
    }

}
