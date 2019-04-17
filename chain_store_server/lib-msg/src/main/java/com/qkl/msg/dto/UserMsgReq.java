package com.qkl.msg.dto;


import com.qkl.common.dto.Dto;


public class UserMsgReq  extends Dto {

	/**主键**/
	private String id;

	/**创建时间**/
	private java.util.Date createTime;

	/**更新时间**/
	private java.util.Date updateTime;

	/**用户id**/
	private String userId;

	/**消息标题**/
	private String msgTitle;

	/**消息内容**/
	private String msgContent;

	/**阅读状态(1未读0已读)**/
	private Integer readStatus;


	private String queryLike; 
	private Integer pageNumber = PAGE_NUMBER;
	private Integer pageSize = PAGE_SIZE; 



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

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setMsgTitle(String msgTitle){
		this.msgTitle = msgTitle;
	}

	public String getMsgTitle(){
		return this.msgTitle;
	}

	public void setMsgContent(String msgContent){
		this.msgContent = msgContent;
	}

	public String getMsgContent(){
		return this.msgContent;
	}

	public void setReadStatus(Integer readStatus){
		this.readStatus = readStatus;
	}

	public Integer getReadStatus(){
		return this.readStatus;
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
