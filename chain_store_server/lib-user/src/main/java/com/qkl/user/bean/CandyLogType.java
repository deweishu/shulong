package com.qkl.user.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * 糖果明细枚举
 * 10.邀请好友20.应用分享30.下载APP40.抽奖所得
 * @author dengjihai
 * @create 2018-08-29
 **/
public class CandyLogType extends JpaProperty {

    public static final CandyLogType SHARE_FRIEND = newInstance(CandyLogType.class, 10, "邀请好友");

    public static final CandyLogType APP_SHARE = newInstance(CandyLogType.class, 20, "分享APP");

    public static final CandyLogType DOWN_APP = newInstance(CandyLogType.class, 30, "下载APP");

    public static final CandyLogType CJ_GET = newInstance(CandyLogType.class, 40, "抽奖所得");

    public static final CandyLogType SIGN_GET = newInstance(CandyLogType.class, 50, "签到成功");

    public static final CandyLogType REG_GET = newInstance(CandyLogType.class, 60, "注册成功");

    public static final CandyLogType COMMENT_APP = newInstance(CandyLogType.class, 70, "评论应用");

}
