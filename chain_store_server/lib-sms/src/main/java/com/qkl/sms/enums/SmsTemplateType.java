package com.qkl.sms.enums;

/**
 * 短信类型
 * Created by dengjihai on 2018/5/15.
 */
public enum SmsTemplateType {

    /** 创蓝普通短信 **/
    CL_SMS(10, "您好，你的验证码是{code}，五分钟内有效。"),
    /** 创蓝语音短信 **/
    CL_VOICE_SMS(20, "您好，您的验证码是{code}，五分钟内有效。"),
    /**发送止损通知短信内容*/
    STOP_STOCK(30, "您的自选股%s已下跌到%s元"),
    /**发送止盈通知短信内容*/
    PROFIT_STOCK(40, "您的自选股%s已上涨到%s元"),

    /** 阿里大鱼默认短信 **/
    ALI_CODE_TEMPLATE(100, "SMS_91785078");

    // 配置其他：如


    private int code;   // 编码
    private String template;// 短信模板

    SmsTemplateType(int code, String template) {
        this.code = code;
        this.template = template;
    }

    /**
     * 根据模板编号获取模板类型
     * @param code
     * @return
     */
    public static SmsTemplateType getByCode(int code) {
        for (SmsTemplateType smsTemplateType : SmsTemplateType.values()) {
            if (code ==  smsTemplateType.getCode()) {
                return smsTemplateType;
            }
        }

        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
