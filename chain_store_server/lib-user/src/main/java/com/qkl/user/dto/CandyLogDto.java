package com.qkl.user.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;


public class CandyLogDto  extends Dto {

	/**主键**/
	private String id;

	/**创建时间**/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;

	/**更新时间**/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;

	/**用户id**/
	private String userId;

	/**发生糖果余额数**/
	private Integer candyNum;

	/**糖果发生改变的原因描述**/
	private String changeDesc;

	/**10.邀请好友20.应用分享30.下载APP40.抽奖所得**/
	@ApiModelProperty(notes = "糖果变动原因：10.邀请好友20.应用分享30.下载APP40.抽奖所得",name = "糖果变动原因：10.邀请好友20.应用分享30.下载APP40.抽奖所得")
	@NotNull(message = "发生类型不能为空")
	private Integer changeType;

	/**对应的对象id**/
	private String changeId;



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

	public void setCandyNum(Integer candyNum){
		this.candyNum = candyNum;
	}

	public Integer getCandyNum(){
		return this.candyNum;
	}

	public void setChangeDesc(String changeDesc){
		this.changeDesc = changeDesc;
	}

	public String getChangeDesc(){
		return this.changeDesc;
	}

	public void setChangeType(Integer changeType){
		this.changeType = changeType;
	}

	public Integer getChangeType(){
		return this.changeType;
	}

	public void setChangeId(String changeId){
		this.changeId = changeId;
	}

	public String getChangeId(){
		return this.changeId;
	}

}
