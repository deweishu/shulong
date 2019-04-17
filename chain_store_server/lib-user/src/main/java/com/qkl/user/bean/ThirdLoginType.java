package com.qkl.user.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * @author dengjihai
 * @create 2018-09-18
 **/
public class ThirdLoginType extends JpaProperty {


    public static final ThirdLoginType WECHAT = newInstance(ThirdLoginType.class, 10, "微信");

    public static final ThirdLoginType QQ_LOGIN = newInstance(ThirdLoginType.class, 20, "QQ");

    public static final ThirdLoginType WEIBO = newInstance(ThirdLoginType.class, 30, "微博");

}
