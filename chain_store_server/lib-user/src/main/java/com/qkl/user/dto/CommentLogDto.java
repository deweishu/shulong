package com.qkl.user.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


@ApiModel(description = "评论app接口请求实体类")
public class CommentLogDto  extends Dto {

	/**主键**/
	private String id;

	/****/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;

	/****/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;

	/**评分数（1-5）**/
	@NotNull(message = "请选择评分数")
	@ApiModelProperty(notes = "评分数",name = "评分数")
	private Integer commentNum;

	/**评分内容**/
	@NotEmpty(message = "请输入评论内容")
	@ApiModelProperty(notes = "评论内容不能超过500字，不能有特殊字符",name = "评论内容不能超过500字，不能有特殊字符")
	private String commentDesc;

	/**用户id**/
	private String userId;

	private String userName;

	/**手机型号**/
	@ApiModelProperty(notes = "手机型号，例如(HUAWEI-P20)",name = "手机型号，例如(HUAWEI-P20)")
	private String mobileType;

	@ApiModelProperty(notes = "应用ID,必传",name = "应用ID,必传")
	@NotEmpty(message = "应用ID不能为空")
	private String apkId;

	private Integer loveNum;//点赞数

	private Integer noNum;//踩 数

	private String commentDate;//评论日期

	public Integer getNoNum() {
		return noNum;
	}

	public void setNoNum(Integer noNum) {
		this.noNum = noNum;
	}

	public Integer getLoveNum() {
		return loveNum;
	}

	public void setLoveNum(Integer loveNum) {
		this.loveNum = loveNum;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApkId() {
		return apkId;
	}

	public void setApkId(String apkId) {
		this.apkId = apkId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

	public void setCommentNum(Integer commentNum){
		this.commentNum = commentNum;
	}

	public Integer getCommentNum(){
		return this.commentNum;
	}

	public void setCommentDesc(String commentDesc){
		this.commentDesc = commentDesc;
	}

	public String getCommentDesc(){
		return this.commentDesc;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setMobileType(String mobileType){
		this.mobileType = mobileType;
	}

	public String getMobileType(){
		return this.mobileType;
	}

}
