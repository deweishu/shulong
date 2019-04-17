package com.qkl.apk.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * 安装包类型---客户端类型
 * @author dengjihai
 * @create 2018-08-30
 **/
public class ApkType extends JpaProperty {


    public static final ApkType ANDROID_APK = newInstance(ApkType.class, 10, "安卓");

    public static final ApkType IOS_APK = newInstance(ApkType.class, 20, "IOS");

}
