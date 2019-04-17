package com.qkl.apk.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * @author dengjihai
 * @create 2018-08-29
 **/
public class ApkVersionStatus extends JpaProperty {

    public static final ApkVersionStatus WAIT_SHENHE = newInstance(ApkVersionStatus.class, 10, "待审核");

    public static final ApkVersionStatus OK_SHENHE = newInstance(ApkVersionStatus.class, 20, "审核通过");

    public static final ApkVersionStatus NO_SHENHE = newInstance(ApkVersionStatus.class, -10, "审核未通过");

    public static final ApkVersionStatus XIAJIA = newInstance(ApkVersionStatus.class, -20, "已下架");

    public static final ApkVersionStatus SHANGJIA = newInstance(ApkVersionStatus.class, 30, "上架中");
}
