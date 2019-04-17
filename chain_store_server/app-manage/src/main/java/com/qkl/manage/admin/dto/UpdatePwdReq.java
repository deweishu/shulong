package com.qkl.manage.admin.dto;

/**
 * 修改密码请求类
 * Created by dengjihai on 2018/3/12.
 */
public class UpdatePwdReq {

    private String originalPwd; //原密码

    private String newPwd;      //新密码

    private String confirmPwd;  //确认密码


    public String getOriginalPwd() {
        return originalPwd;
    }

    public void setOriginalPwd(String originalPwd) {
        this.originalPwd = originalPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }
}
