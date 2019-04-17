package com.qkl.sms.service;

/**
 * Created by niejiuqian on 2017/9/8.
 */
public interface SmsSendCallback {
    /**
     * 发送验证吗完成回调
     * @param result 发送结果 成功/失败
     * @param msg 发送成功/失败描述
     */
    void call(boolean result, String msg);
}
