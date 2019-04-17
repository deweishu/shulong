package com.qkl.api.app.dto;

import com.qkl.apk.dto.ImgDto;
import com.qkl.apk.dto.VersionDto;
import com.qkl.user.dto.CommentLogDto;
import com.qkl.user.entity.CommentLog;

import java.io.Serializable;
import java.util.List;

public class AppInfoResp implements Serializable {


    private VersionDto versionDto;

    private List<CommentLogDto> commentList;

    private List<ImgDto> imgList;

    public List<ImgDto> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgDto> imgList) {
        this.imgList = imgList;
    }

    public List<CommentLogDto> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentLogDto> commentList) {
        this.commentList = commentList;
    }

    public VersionDto getVersionDto() {
        return versionDto;
    }

    public void setVersionDto(VersionDto versionDto) {
        this.versionDto = versionDto;
    }
}
