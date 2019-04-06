package com.tensquare.sms;


import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;



    @Value("${aliyun.sms.template_code}")
    private String template_code;

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;

    @RabbitHandler
    public void sendSms(Map<String,String> map) throws ClientException {

        //1. 取出消息(手机号码, 验证码)
        String mobile = map.get("mobile");
        String code = map.get("code");
        System.out.println("手机号码:"+mobile + ",的验证码是:" + code);

        //2.发生短信
        //smsUtil.sendSms(String mobile,String template_code,String sign_name,String param)
        smsUtil.sendSms(mobile,template_code,sign_name,"{\"code\":\""+code+"\"}");

    }

}
