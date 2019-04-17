package com.qkl.apk.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * 专题
 * @author dengjihai
 * @create 2018-08-24
 **/
public class AppCategory extends JpaProperty {


    public static final AppCategory NEW_APP = newInstance(AppCategory.class, 10, "新品推荐");

    public static final AppCategory MUST_APP = newInstance(AppCategory.class, 20, "入门必备");

    public static final AppCategory TODAY_APP = newInstance(AppCategory.class, 30, "今日主题");

}
