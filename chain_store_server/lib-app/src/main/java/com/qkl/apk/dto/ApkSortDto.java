package com.qkl.apk.dto;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2018-09-20
 **/
public class ApkSortDto implements Serializable {


    private String apkIds;

    private Integer sortNum;

    private Integer sortType;

    public String getApkIds() {
        return apkIds;
    }

    public void setApkIds(String apkIds) {
        this.apkIds = apkIds;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
