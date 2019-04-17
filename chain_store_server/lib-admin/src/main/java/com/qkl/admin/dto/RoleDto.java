package com.qkl.admin.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;

import java.util.Date;

/**
 * Created by dengjihai on 2018/3/9.
 */
public class RoleDto extends Dto {

    private String id;

    private String name;

    private Integer status;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    public RoleDto() {
    }

    public RoleDto(String id, String name, Integer status, Date createTime) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
