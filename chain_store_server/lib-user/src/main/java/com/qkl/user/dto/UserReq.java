package com.qkl.user.dto;


import com.qkl.common.dto.Dto;


public class UserReq  extends Dto {

	/**主键**/
	private String id;

	/****/
	private java.util.Date createTime;

	/****/
	private java.util.Date updateTime;

	/**手机号**/
	private String mobile;

	/**登录密码**/
	private String passWord;

	/**是否封号**/
	private Integer status;

	/**头像url**/
	private String headImg;

	/**用户糖果余额**/
	private Integer candyAmount;

	/**用户名**/
	private String nickName;

	/**邀请人id**/
	private String invetId;


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

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return this.mobile;
	}

	public void setPassWord(String passWord){
		this.passWord = passWord;
	}

	public String getPassWord(){
		return this.passWord;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setHeadImg(String headImg){
		this.headImg = headImg;
	}

	public String getHeadImg(){
		return this.headImg;
	}

	public void setCandyAmount(Integer candyAmount){
		this.candyAmount = candyAmount;
	}

	public Integer getCandyAmount(){
		return this.candyAmount;
	}

	public void setNickName(String nickName){
		this.nickName = nickName;
	}

	public String getNickName(){
		return this.nickName;
	}

	public void setInvetId(String invetId){
		this.invetId = invetId;
	}

	public String getInvetId(){
		return this.invetId;
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
