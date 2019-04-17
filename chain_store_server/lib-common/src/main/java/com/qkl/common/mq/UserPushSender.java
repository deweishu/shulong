package com.qkl.common.mq;

import org.springframework.stereotype.Component;

/**
 * @author dengjihai
 * @create 2018-10-31
 **/
@Component
public class UserPushSender extends MQSender {

    public static void send(String msgId){
        send(MQConstant.USER_MSG_REGISTER, msgId);
    }

}
