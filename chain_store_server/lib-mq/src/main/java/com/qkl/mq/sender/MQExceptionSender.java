package com.qkl.mq.sender;


import com.qkl.common.mq.MQConstant;
import com.qkl.common.mq.MQSender;
import org.springframework.stereotype.Component;

/**
 * Created by linyh on 2017/8/26.
 */
@Component
public class MQExceptionSender extends MQSender {

    public static void sendToMQException(String message){
        send(MQConstant.MQ_EXCEPTION,message);
    }
}
