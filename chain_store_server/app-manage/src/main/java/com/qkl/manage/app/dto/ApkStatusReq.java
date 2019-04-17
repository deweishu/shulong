package com.qkl.manage.app.dto;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2018-08-29
 **/
public class ApkStatusReq implements Serializable {

    private Integer status;

    private String id;

    private String statusTxt;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatusTxt() {
        return statusTxt;
    }

    public void setStatusTxt(String statusTxt) {
        this.statusTxt = statusTxt;
    }
}
