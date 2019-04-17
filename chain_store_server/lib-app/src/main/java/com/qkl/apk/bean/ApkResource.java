package com.qkl.apk.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * 应用来源(10.系统添加20.客户添加)
 *
 * @author dengjihai
 * @create 2018-08-24
 **/
public class ApkResource extends JpaProperty {

    public static final ApkResource SYS_ADD = newInstance(ApkResource.class, 10, "系统添加");

    public static final ApkResource CUSTOMER_ADD = newInstance(ApkResource.class, 20, "客户添加");

}
