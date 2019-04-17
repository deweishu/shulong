package com.qkl.sms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 短信发送返回结果
 * Created by dengjihai on 2018/5/15.
 */
public class SendSmsResponse {

    @JsonProperty("RequestId")
    private String requestId;

    @JsonProperty("BizId")
    private String bizId;

    @JsonProperty("Code")
    private String code;

    @JsonProperty("Message")
    private String message;

    public SendSmsResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    @Override
    public String toString() {
        return "SendSmsResponse{" +
                "requestId='" + requestId + '\'' +
                ", bizId='" + bizId + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
