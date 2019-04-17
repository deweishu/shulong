package com.qkl.admin.dto;

import com.qkl.common.dto.PageDto;

/**
 * Created by dengjihai on 2018/3/9.
 */
public class RoleReq extends PageDto {

    private String queryLike;

    public String getQueryLike() {
        return queryLike;
    }

    public void setQueryLike(String queryLike) {
        this.queryLike = queryLike;
    }
}
