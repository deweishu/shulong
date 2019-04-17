package com.qkl.manage.app.dto;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2018-09-03
 **/
public class ApkZtReq implements Serializable {

    private String apkIds;

    private String cateId;

    private String ztDate;


    public String getZtDate() {
        return ztDate;
    }

    public void setZtDate(String ztDate) {
        this.ztDate = ztDate;
    }

    public String getApkIds() {
        return apkIds;
    }

    public void setApkIds(String apkIds) {
        this.apkIds = apkIds;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }
}
