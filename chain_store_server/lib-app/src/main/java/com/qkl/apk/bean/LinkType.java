package com.qkl.apk.bean;

import com.qkl.common.bean.JpaProperty;

public class LinkType extends JpaProperty {

    public static final LinkType SELF_LINK = newInstance(LinkType.class, 30, "安装包下载");

    public static final LinkType APP_STORE = newInstance(LinkType.class, 10, "AppStore链接");

//    public static final LinkType THIRD_LINK = newInstance(LinkType.class, 20, "第三方分发平台链接");


}

