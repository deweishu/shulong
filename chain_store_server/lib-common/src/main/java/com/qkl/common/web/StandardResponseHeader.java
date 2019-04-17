package com.qkl.common.web;

/**
 * 标准响应头
 */
public class StandardResponseHeader {

    public static final ResponseHeader SUCCESS = ResponseHeader.newInstance(0, "成功");

    public static final ResponseHeader ERROR = ResponseHeader.newInstance(105000, "系统错误");

    public static final ResponseHeader ERROR_TOCKEN = ResponseHeader.newInstance(105001, "token错误");

    public static final ResponseHeader ERROR_TOCKEN_EXPIRE = ResponseHeader.newInstance(105002, "token失效");

    public static final ResponseHeader ERROR_SIGN = ResponseHeader.newInstance(105010, "签名错误");

    public static final ResponseHeader USER_NOT_EXISTS = ResponseHeader.newInstance(105021, "用户不存在");

    public static final ResponseHeader USER_REGISTERED = ResponseHeader.newInstance(105022, "用户已注册");


    public static final ResponseHeader ERROR_PARAM = ResponseHeader.newInstance(100000, "请求参数有误");


    public static final ResponseHeader SEND_SMS_FAIL = ResponseHeader.newInstance(100004, "发送短信失败");

    public static final ResponseHeader SEND_SMS_OUT_NUM = ResponseHeader.newInstance(100014, "发送短信频率过大");

    public static final ResponseHeader SEND_SMS_MAX_NUM = ResponseHeader.newInstance(100015, "今日发送短信次数达到限制");


    public static final ResponseHeader ERROR_GENERATE_INVITE_CODE = ResponseHeader.newInstance(101005, "生成邀请码时系统繁忙，请稍后重试");

}
