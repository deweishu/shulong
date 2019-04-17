package com.qkl.api.app.dto;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2018-09-20
 **/
public class AppCheckUpdate implements Serializable {

    private  String packageName;


    //getter and setter
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
