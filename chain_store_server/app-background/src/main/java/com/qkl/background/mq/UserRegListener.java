package com.qkl.background.mq;

import com.qkl.common.mq.MQConstant;
import com.qkl.common.util.StringUtil;
import com.qkl.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dengjihai
 * @create 2018-09-11
 **/
@Configuration
@RabbitListener(queues = MQConstant.USER_REGITER)
public class UserRegListener extends AbstractMQListener {

    public static final Logger logger = LoggerFactory.getLogger(UserRegListener.class);

    @Autowired
    UserService userService;

    @Bean(MQConstant.USER_REGITER)
    public Queue fooQueue() {
        return new Queue(MQConstant.USER_REGITER);
    }


    @Override
    protected void doProcess(String message) {
        logger.info("\n ### 接收到用户邀请注册消息：{}", message);
        if(StringUtil.isBlank(message)){
            return;
        }
        userService.addInvetCandy(message);
    }
}
