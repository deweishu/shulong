package com.qkl.apk.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * app类型(10.安卓20.ios)
 * @author dengjihai
 * @create 2018-08-24
 **/
public class AppType extends JpaProperty {


    public static final AppType ANDROID_APP = newInstance(AppType.class, 10, "安卓");

    public static final AppType IOS_APP = newInstance(AppType.class, 20, "IOS");

    public static final AppType ALL_APP = newInstance(AppType.class, 30, "安卓+IOS");


}
