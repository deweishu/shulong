package com.qkl.sms.dto;

/**
 * 发送短信结果
 * Created by dengjihai on 2018/5/15.
 */
public class SendSmsResult {

    private boolean result;
    private String msg;

    public boolean isSuccess() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
