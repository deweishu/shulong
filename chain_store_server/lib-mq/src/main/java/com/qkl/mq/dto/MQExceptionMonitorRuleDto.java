package com.qkl.mq.dto;


import com.qkl.common.dto.Dto;

public class MQExceptionMonitorRuleDto extends Dto {

    private String id;
    private String queueName; //队列名称
    private int threshold;    //阀值
    private String receiverName;  //告警消息接受者名称
    private String receiverPhone; //告警消息接受者电话

    public MQExceptionMonitorRuleDto() {
    }

    public MQExceptionMonitorRuleDto(String id, String queueName, int threshold,
                                     String receiverName, String receiverPhone) {
        this.id = id;
        this.queueName = queueName;
        this.threshold = threshold;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    @Override
    public String toString() {
        return "MQExceptionMonitorRuleDto{" +
                "queueName='" + queueName + '\'' +
                ", threshold=" + threshold +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                '}';
    }
}
