package com.qkl.common.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by dengjihai on 2018/3/28.
 */
public class PageDto extends Dto {

    @ApiModelProperty(value = "页码", name = "pageNumber", required = true)
    @NotNull(message = "页码不能为空")
    private Integer pageNumber = PAGE_NUMBER;
    @ApiModelProperty(value = "每页行数", name = "pageSize", required = true)
    @NotNull(message = "每页记录数不能为空")
    private Integer pageSize = PAGE_SIZE;

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
