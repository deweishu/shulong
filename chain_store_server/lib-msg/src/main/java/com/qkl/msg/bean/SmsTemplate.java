package com.qkl.msg.bean;


import com.qkl.common.bean.JpaProperty;

/**
 * 短信模板
 * Created by dengjihai on 2018/8/10.
 */
public class SmsTemplate extends JpaProperty {

    // 短信消息
    public static final SmsTemplate CL_SMS = newInstance(SmsTemplate.class, 0,"短信发送（发送内容自定义）");

    // 短信验证码，默认
    public static final SmsTemplate CL_SMS_NORMAL = newInstance(SmsTemplate.class, 10,"您的验证码是{code}，请在5分钟内进行验证。");

    // 语音短信
    public static final SmsTemplate CL_VOICE_SMS = newInstance(SmsTemplate.class, 20,"您的验证码是{code}，五分钟内有效。");

    // 30开始是股票型短信模板
    public static final SmsTemplate CL_STOCK_STOP = newInstance(SmsTemplate.class, 30,"您的自选股%s已下跌到%s元");

    public static final SmsTemplate CL_STOCK_PROFIT = newInstance(SmsTemplate.class, 31,"您的自选股%s已上涨到%s元");

    public static final SmsTemplate CL_STOCK_INVEST_NOTIFY = newInstance(SmsTemplate.class, 32,"今日投资布局已发布，可登录公众号查看，不要错过哦！");


    // 其他模板，在此处添加



    /**
     * 获取短信模板
     * @param code 模板编码，若为null，则返回默认模板
     * @return 短信模板
     */
    public static SmsTemplate getTemplateByCode(Integer code){
        if (null == code) return CL_SMS_NORMAL;
        SmsTemplate temp = getProperty(SmsTemplate.class,code);
        return null == temp ? CL_SMS_NORMAL : temp;
    }

}
