package com.qkl.apk.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * @author dengjihai
 * @create 2018-08-28
 **/
public class BannerLinkType extends JpaProperty {

    public static final BannerLinkType WEB_SITE_URL = newInstance(BannerLinkType.class, 10, "网页链接");

    public static final BannerLinkType APP_INFO = newInstance(BannerLinkType.class, 20, "指定APP");

    public static final BannerLinkType NONE_DATA = newInstance(BannerLinkType.class, 30, "不指定");



}
