package com.qkl.sms.dto;

/**
 * 普通短信发送响应实体类
 * Created by dengjihai on 2018/5/15.
 */
public class ClSmsSendResponse {

    /**
     * 状态码（详细参考提交响应状态码）
     */
    private String code;

    /**
     * 状态码说明（成功返回空）
     */
    private String errorMsg;

    /**
     * 响应时间
     */
    private String time;

    /**
     * 消息id
     */
    private String msgId;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "ClSmsSendResponse{" +
                "code='" + code + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", time='" + time + '\'' +
                ", msgId='" + msgId + '\'' +
                '}';
    }
}
