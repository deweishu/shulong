package com.qkl.admin.bean;

import com.qkl.common.bean.JpaProperty;
import com.qkl.common.bean.JpaStringProperty;

public class SysConfigKey extends JpaStringProperty {


    public static final SysConfigKey REG_CANDY = newInstance(SysConfigKey.class, "REG_CANDY", "注册送糖果数");

    public static final SysConfigKey SIGN_CANDY = newInstance(SysConfigKey.class, "SIGN_CANDY", "签到送糖果数");

    public static final SysConfigKey INVITE_FRIEND = newInstance(SysConfigKey.class, "INVITE_FRIEND", "邀请1级好友送糖果数");

    public static final SysConfigKey INVITE_FRIEND_SECOND = newInstance(SysConfigKey.class, "INVITE_FRIEND_SECOND", "邀请2级好友送糖果数");

    public static final SysConfigKey INVITE_FRIEND_THIRD = newInstance(SysConfigKey.class, "INVITE_FRIEND_THIRD", "邀请3级好友送糖果数");

    public static final SysConfigKey SHARE_APP = newInstance(SysConfigKey.class, "SHARE_APP", "应用分享送糖果");

    public static final SysConfigKey DOWN_APP = newInstance(SysConfigKey.class, "DOWN_APP", "下载APP送糖果");

    public static final SysConfigKey CJ_CANDY = newInstance(SysConfigKey.class, "CJ_CANDY", "抽奖送糖果数");

    public static final SysConfigKey CJ_NUM = newInstance(SysConfigKey.class, "CJ_NUM", "签到7天抽糖送糖果数范围,例如(10-1000)");

    public static final SysConfigKey CANDY_DESC = newInstance(SysConfigKey.class, "CANDY_DESC", "糖果介绍");

    public static final SysConfigKey SIGN_RULE = newInstance(SysConfigKey.class, "SIGN_RULE", "签到规则描述");

    public static final SysConfigKey MAX_APP_NUM = newInstance(SysConfigKey.class, "MAX_APP_NUM", "合作商最多创建几个应用");

    public static final SysConfigKey APP_IMG_MAX_NUM = newInstance(SysConfigKey.class, "APP_IMG_MAX_NUM", "应用演示图最多上传几张");



}
