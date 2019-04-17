package com.qkl.common.dto;

/**
 * Created by dengj on 2017/9/5.
 */
public class PageResponse<T> {


    private int showCount; //每页显示记录数
    private int totalPage;		//总页数
    private int totalResult;	//总记录数
    private int currentPage;	//当前页
    private T data;


    public int getShowCount() {
        return showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public int getTotalPage() {
        if(totalResult%showCount==0)
            totalPage = totalResult/showCount;
        else
            totalPage = totalResult/showCount+1;
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getCurrentPage() {
        if(currentPage<=0)
            currentPage = 1;
        if(currentPage>getTotalPage())
            currentPage = getTotalPage();
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
