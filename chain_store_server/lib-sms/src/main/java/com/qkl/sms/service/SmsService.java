package com.qkl.sms.service;

import com.qkl.sms.util.ChuangLanSmsUtil;
import org.springframework.stereotype.Service;

/**
 * 短信接口
 * Created by dengjihai on 2018/5/15.
 */
@Service
public class SmsService {


    /**
     * 发送文本短信
     * @param content 发送内容
     * @param phoneNum 接收短信手机号
     * @param callback 回调接口
     */
    public void sendSms(String content, String phoneNum, SmsSendCallback callback){
        ChuangLanSmsUtil.sendSms(phoneNum, content, callback);
    }


    /**
     * 发送语音短信
     * @param content 发送内容
     * @param phoneNum 接收短信手机号
     * @param callback 回调接口
     */
    public void sendVoiceSms(String content, String phoneNum, SmsSendCallback callback){
        ChuangLanSmsUtil.sendVoiceSms(phoneNum, content, callback);
    }

}
