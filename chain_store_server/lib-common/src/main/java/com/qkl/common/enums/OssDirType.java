package com.qkl.common.enums;

/**
 * OSS 目录枚举
 * Created by dengjihai on 2018/6/1.
 */
public enum OssDirType {

    APK("apk", "apk包目录"),
    PLIST("ios", "iosPlist目录"),
    IMG_PATH("img", "图片存在路径");

    private String dir;     // 目录
    private String desc;    // 描述

    OssDirType(String dir, String desc) {
        this.dir = dir;
        this.desc = desc;
    }

    public String getDir() {
        return dir;
    }

    public String getDesc() {
        return desc;
    }
}
