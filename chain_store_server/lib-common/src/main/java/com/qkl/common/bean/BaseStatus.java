package com.qkl.common.bean;

/**
 * 基础状态
 * Created by dengjihai on 2018/3/7.
 */
public class BaseStatus extends JpaProperty {

    public static final BaseStatus COMMON = newInstance(BaseStatus.class, 10, "正常");

    public static final BaseStatus DISABLED = newInstance(BaseStatus.class, -10, "停用");

}
