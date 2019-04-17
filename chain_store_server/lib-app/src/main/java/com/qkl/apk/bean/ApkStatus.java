package com.qkl.apk.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * 应用状态(10.上架中-10已下架20待审核30审核通过40审核未通过)
 * @author dengjihai
 * @create 2018-08-24
 **/
public class ApkStatus extends JpaProperty {



    public static final ApkStatus DISABLE = newInstance(ApkStatus.class, -10, "已下架");

    public static final ApkStatus NORMAL = newInstance(ApkStatus.class, 10, "上架中");

    public static final ApkStatus DAI_SHENHE = newInstance(ApkStatus.class, 20, "待审核");

    public static final ApkStatus OK_SHENHE = newInstance(ApkStatus.class, 30, "审核通过");

    public static final ApkStatus NO_SHENHE = newInstance(ApkStatus.class, 40, "审核未通过");


}
