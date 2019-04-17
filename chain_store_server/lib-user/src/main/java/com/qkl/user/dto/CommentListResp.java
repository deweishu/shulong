package com.qkl.user.dto;

import org.springframework.data.domain.Page;

import java.io.Serializable;

public class CommentListResp implements Serializable {


    private String totalScore;//总评分

    private Integer goodScoreNum;//好评数

    private Integer soScoreNum;//中评数

    private Integer badScoreNum;//差评数、


    private Page<CommentLogDto> commentData;


    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getGoodScoreNum() {
        return goodScoreNum;
    }

    public void setGoodScoreNum(Integer goodScoreNum) {
        this.goodScoreNum = goodScoreNum;
    }

    public Integer getSoScoreNum() {
        return soScoreNum;
    }

    public void setSoScoreNum(Integer soScoreNum) {
        this.soScoreNum = soScoreNum;
    }

    public Integer getBadScoreNum() {
        return badScoreNum;
    }

    public void setBadScoreNum(Integer badScoreNum) {
        this.badScoreNum = badScoreNum;
    }

    public Page<CommentLogDto> getCommentData() {
        return commentData;
    }

    public void setCommentData(Page<CommentLogDto> commentData) {
        this.commentData = commentData;
    }
}
