package com.qkl.user.dto;


import com.qkl.common.dto.Dto;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;


public class CommentLogReq  extends Dto {

	/**主键**/
	private String id;

	/****/
	private java.util.Date createTime;

	/****/
	private java.util.Date updateTime;

	/**评分数（1-5）**/
	private Integer commentNum;

	/**评分内容**/
	private String commentDesc;

	/**用户id**/
	private String userId;

	/**手机型号**/
	private String mobileType;


	private String queryLike; 
	private Integer pageNumber = PAGE_NUMBER;
	private Integer pageSize = PAGE_SIZE;

	@ApiModelProperty (notes = "应用ID，必传",name = "应用ID，必传")
	@NotEmpty(message = "应用ID不能为空")
	private String apkId;




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
