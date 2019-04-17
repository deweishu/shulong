package com.qkl.admin.mq;

import com.qkl.common.mq.MQConstant;
import com.qkl.common.mq.MQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试消息发送器
 * Created by dengjihai on 2018/3/7.
 */
@Component
public class TestMessageSender extends MQSender {

    public static final Logger logger = LoggerFactory.getLogger(TestMessageSender.class);

    /**
     * 发送消息
     * @param msg
     */
    public static void send(String msg){
        logger.info("\n######调用测试消息发送器，发送内容：{}", msg);
        send(MQConstant.TEST_MESSAGE, msg);
    }
}
