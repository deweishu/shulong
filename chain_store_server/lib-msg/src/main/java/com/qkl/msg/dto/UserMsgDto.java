package com.qkl.msg.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;

import java.util.Date;


public class UserMsgDto  extends Dto {

	/**主键**/
	private String id;

	/**创建时间**/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;

	/**更新时间**/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;

	@JSONField(format = "yyyy-MM-dd")
	private java.util.Date createDate;

	/**用户id**/
	private String userId;

	/**消息标题**/
	private String msgTitle;

	/**消息内容**/
	private String msgContent;

	/**阅读状态(1未读0已读)**/
	private Boolean readStatus;


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public void setReadStatus(Boolean readStatus){
		this.readStatus = readStatus;
	}

	public Boolean getReadStatus(){
		return this.readStatus;
	}

}
