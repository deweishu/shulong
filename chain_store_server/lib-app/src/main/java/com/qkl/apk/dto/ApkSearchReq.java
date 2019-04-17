package com.qkl.apk.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(description = "排行分类查询请求实体类")
public class ApkSearchReq implements Serializable {

    @ApiModelProperty(name = "分类id",notes = "分类id")
    private String categoryId;//分类id


    @ApiModelProperty(notes = "排序类型，10，新品榜20飙升榜，30应用榜，40游戏榜",name = "排序类型，10，新品榜20飙升榜，30应用榜，40游戏榜")
    private Integer sortType;//排序类型

    @ApiModelProperty(notes = "当前页码",name = "当前页码")
    @NotNull(message = "当前页码不能为空")
    private Integer pageNo;

    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getPageNo() {
        return pageNo<1?1:pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
