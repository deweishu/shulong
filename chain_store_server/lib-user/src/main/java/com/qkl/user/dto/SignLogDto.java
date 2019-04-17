package com.qkl.user.dto;


import com.qkl.common.dto.Dto;


public class SignLogDto  extends Dto {

	/**主键**/
	private String id;

	/****/
	private java.util.Date createTime;

	/****/
	private java.util.Date updateTime;

	/**用户id**/
	private String userId;

	/**签到日期**/
	private String signDate;

	/**签到获得的糖果数**/
	private Integer candyNum;

	private String signIp;

	public String getSignIp() {
		return signIp;
	}

	public void setSignIp(String signIp) {
		this.signIp = signIp;
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

	public void setSignDate(String signDate){
		this.signDate = signDate;
	}

	public String getSignDate(){
		return this.signDate;
	}

	public void setCandyNum(Integer candyNum){
		this.candyNum = candyNum;
	}

	public Integer getCandyNum(){
		return this.candyNum;
	}

}
