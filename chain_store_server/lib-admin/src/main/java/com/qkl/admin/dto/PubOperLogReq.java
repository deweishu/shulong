package com.qkl.admin.dto;


import com.qkl.common.dto.Dto;

/**
 * Created by Benson on 2018/3/13.
 */
public class PubOperLogReq extends Dto {

    private String queryLike;
    private Integer pageNumber = PAGE_NUMBER;
    private Integer pageSize = PAGE_SIZE;

    public String getQueryLike() {
        return queryLike;
    }

    public void setQueryLike(String queryLike) {
        this.queryLike = queryLike;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
