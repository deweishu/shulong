package com.qkl.common.dto;

/**
 * 底层可组装加载
 * Created by dengjihai on 2018/3/28.
 **/
public interface ILoad {

    /** 组装对象 */
    ILoad load(Object[] o);
}