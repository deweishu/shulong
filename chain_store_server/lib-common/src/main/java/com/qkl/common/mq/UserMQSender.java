package com.qkl.common.mq;

import org.springframework.stereotype.Component;


@Component
public class UserMQSender extends MQSender {

    public static void send(String invetCode){
        send(MQConstant.USER_REGITER, invetCode);
    }


}
