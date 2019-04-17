package com.qkl.background.mq;

import com.qkl.common.mq.MQConstant;
import com.qkl.common.util.StringUtil;
import com.qkl.msg.dto.PushLogDto;
import com.qkl.msg.dto.UserMsgDto;
import com.qkl.msg.entity.PushLog;
import com.qkl.msg.entity.UserMsg;
import com.qkl.msg.jpa.UserMsgService;
import com.qkl.msg.service.PushService;
import com.qkl.user.dto.UserDto;
import com.qkl.user.entity.User;
import com.qkl.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.ArrayList;
import java.util.List;

/**
 * @author dengjihai
 * @create 2018-10-31
 **/
@Configuration
@RabbitListener(queues = MQConstant.USER_MSG_REGISTER)
public class UserPushListener extends AbstractMQListener {


    public static final Logger logger = LoggerFactory.getLogger(UserPushListener.class);

    @Autowired
    UserService userService;

    @Autowired
    PushService pushService;


    @Autowired
    UserMsgService msgService;

    @Bean(MQConstant.USER_MSG_REGISTER)
    public Queue fooQueue() {
        return new Queue(MQConstant.USER_MSG_REGISTER);
    }

    @Override
    protected void doProcess(String msgId) {
        logger.info("\n ### 接收到推送消息给用户请求：{}", msgId);
        if(StringUtil.isBlank(msgId)){
            return;
        }
        PushLogDto pushLogDto=pushService.findOne(msgId);
        List<UserDto> userDtoList =userService.findAll();
        List<UserMsgDto> userMsgDtoList =new ArrayList<>();
        userDtoList.forEach(userDto -> {
            UserMsgDto userMsgDto= new UserMsgDto();
            userMsgDto.setUserId(userDto.getId());
            userMsgDto.setMsgTitle(pushLogDto.getPushTitle());
            userMsgDto.setMsgContent(pushLogDto.getPushTxt());
            userMsgDto.setReadStatus(true);//默认是未读状态
            userMsgDtoList.add(userMsgDto);
        });
       msgService.save(userMsgDtoList);

    }
}
