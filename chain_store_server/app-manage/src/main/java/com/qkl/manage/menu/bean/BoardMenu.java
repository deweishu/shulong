package com.qkl.manage.menu.bean;


import com.qkl.menu.MenuDefiner;
import com.qkl.menu.MenuType;

/**
 * 导航菜单（一级菜单）
 * Created by dengjihai on 2018/3/7.
 */
@MenuType
public class BoardMenu {

    @MenuDefiner(name = "系统管理", icon = "fa fa-cog", priority = 10)
    public static final String ADMIN = "admin";

    @MenuDefiner(name = "会员管理", icon = "fa fa-user", priority = 9)
    public static final String MEMBER = "member";

    @MenuDefiner(name = "合作商管理", icon = "fa fa-users", priority = 8)
    public static final String PARTNER = "partner";

    @MenuDefiner(name = "应用管理", icon = "fa fa-th-large", priority = 7)
    public static final String APP_MARKET = "app_market";

    @MenuDefiner(name = "广告活动管理", icon = "fa fa-diamond", priority = 9)
    public static final String ADVERT_ACTIVITY = "advert_activity";

    @MenuDefiner(name = "钱包管理", icon = "fa fa-wallet", priority = 9)
    public static final String WALLET = "wallet";

}
