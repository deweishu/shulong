package com.qkl.apk.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * app-category-type
 */
public class CategoryType extends JpaProperty {

    public static final CategoryType FENLEI = newInstance(CategoryType.class, 10, "分类");

    public static final CategoryType ZHUANTI = newInstance(CategoryType.class, 20, "专题");

}
