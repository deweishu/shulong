package com.qkl.apk.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * @author dengjihai
 * @create 2018-08-28
 **/
public class AcitivityType extends JpaProperty {


    public static final AcitivityType PROJECT_ACTIVITY = newInstance(AcitivityType.class, 10, "项目活动");

    public static final AcitivityType CANDY_ACIVITY = newInstance(AcitivityType.class, 20, "糖果盒子");

    public static final AcitivityType SHANGJIN_ACTIVITY = newInstance(AcitivityType.class, 30, "赏金计划");


}
