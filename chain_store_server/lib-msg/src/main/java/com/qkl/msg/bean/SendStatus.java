package com.qkl.msg.bean;


import com.qkl.common.bean.JpaProperty;

/**
 * Created by 6299 on 2018/5/31.
 * Modify by dengjihai on 2018/8/10.
 */
public class SendStatus extends JpaProperty {

    public static final SendStatus SENDED = newInstance(SendStatus.class, 10,"已发送");

    public static final SendStatus SENDED_SUCCESS = newInstance(SendStatus.class, 20,"发送成功");

    public static final SendStatus SENDED_ERROR = newInstance(SendStatus.class, 30,"发送失败");

}
