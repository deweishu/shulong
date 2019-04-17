package com.qkl.api.app.dto;

import com.qkl.apk.dto.ApkDto;
import com.qkl.apk.es.EsApk;

import java.io.Serializable;
import java.util.List;

/**
 * @author dengjihai
 * @create 2018-09-11
 **/
public class AppListResp implements Serializable {

    private List<ApkDto> appList;

    private List<ApkDto> todayList;

    public List<ApkDto> getAppList() {
        return appList;
    }

    public void setAppList(List<ApkDto> appList) {
        this.appList = appList;
    }

    public List<ApkDto> getTodayList() {
        return todayList;
    }

    public void setTodayList(List<ApkDto> todayList) {
        this.todayList = todayList;
    }
}
