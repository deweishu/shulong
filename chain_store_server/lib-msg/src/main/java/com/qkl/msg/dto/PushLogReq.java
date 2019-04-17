package com.qkl.msg.dto;


import com.qkl.common.dto.Dto;


public class PushLogReq  extends Dto {

	/**主键**/
	private String id;

	/**创建时间**/
	private java.util.Date createTime;

	/**更新时间**/
	private java.util.Date updateTime;

	/**推送通知栏的标题**/
	private String pushTitle;

	/**推送通知栏的描述**/
	private String pushTxt;

	/**推送内容（透传消息）**/
	private String pushContent;

	/**操作者id**/
	private String operateId;

	/**操作者名称**/
	private String operateName;

	/**10.单个推送20.群推**/
	private Integer pushType;

	/**推送结果**/
	private String pushResult;


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

	public void setPushTitle(String pushTitle){
		this.pushTitle = pushTitle;
	}

	public String getPushTitle(){
		return this.pushTitle;
	}

	public void setPushTxt(String pushTxt){
		this.pushTxt = pushTxt;
	}

	public String getPushTxt(){
		return this.pushTxt;
	}

	public void setPushContent(String pushContent){
		this.pushContent = pushContent;
	}

	public String getPushContent(){
		return this.pushContent;
	}

	public void setOperateId(String operateId){
		this.operateId = operateId;
	}

	public String getOperateId(){
		return this.operateId;
	}

	public void setOperateName(String operateName){
		this.operateName = operateName;
	}

	public String getOperateName(){
		return this.operateName;
	}

	public void setPushType(Integer pushType){
		this.pushType = pushType;
	}

	public Integer getPushType(){
		return this.pushType;
	}

	public void setPushResult(String pushResult){
		this.pushResult = pushResult;
	}

	public String getPushResult(){
		return this.pushResult;
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
