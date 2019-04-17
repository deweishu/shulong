package com.qkl.common.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * 应用中的无返回类型，相当于java中的void
 * Created by Benson on 2018/5/16.
 */
@ApiModel(value = "无返回类型对象")
public class AppVoid implements Serializable {

    private static class SingletonAppVoid {
        private static AppVoid INSTANCE = new AppVoid();
    }

    private AppVoid() {
    }

    public static AppVoid getInstance(){
        return SingletonAppVoid.INSTANCE;
    }
}
