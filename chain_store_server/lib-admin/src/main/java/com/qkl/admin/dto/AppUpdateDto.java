package com.qkl.admin.dto;

import java.io.Serializable;
import java.util.Date;

public class AppUpdateDto implements Serializable {

    private String update;
    private String new_version;
    private String apk_file_url;
    private String update_log;
    private String target_size;
    private String new_md5;
    private boolean constraint;
    public void setUpdate(String update) {
        this.update = update;
    }
    public String getUpdate() {
        return update;
    }

    public void setNew_version(String new_version) {
        this.new_version = new_version;
    }
    public String getNew_version() {
        return new_version;
    }

    public void setApk_file_url(String apk_file_url) {
        this.apk_file_url = apk_file_url;
    }
    public String getApk_file_url() {
        return apk_file_url;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }
    public String getUpdate_log() {
        return update_log;
    }

    public void setTarget_size(String target_size) {
        this.target_size = target_size;
    }
    public String getTarget_size() {
        return target_size;
    }

    public void setNew_md5(String new_md5) {
        this.new_md5 = new_md5;
    }
    public String getNew_md5() {
        return new_md5;
    }

    public void setConstraint(boolean constraint) {
        this.constraint = constraint;
    }
    public boolean getConstraint() {
        return constraint;
    }
}
