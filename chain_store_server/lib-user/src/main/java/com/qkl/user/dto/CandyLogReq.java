package com.qkl.user.dto;


import com.qkl.common.dto.Dto;


public class CandyLogReq  extends Dto {

	/**主键**/
	private Integer id;

	/**创建时间**/
	private java.util.Date createTime;

	/**更新时间**/
	private java.util.Date updateTime;

	/**用户id**/
	private String userId;

	/**发生糖果余额数**/
	private Integer candyNum;

	/**糖果发生改变的原因描述**/
	private String changeDesc;

	/**10.邀请好友20.应用分享30.下载APP40.抽奖所得**/
	private Integer changeType;

	/**对应的对象id**/
	private String changeId;


	private String queryLike; 
	private Integer pageNumber = PAGE_NUMBER;
	private Integer pageSize = PAGE_SIZE; 



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
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
