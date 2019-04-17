package com.qkl.user.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;
import com.qkl.user.bean.ThirdLoginType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


public class UserDto  extends Dto {

	/**主键**/
	private String id;

	/****/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;

	/****/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;

	/**手机号**/
	private String mobile;

	/**登录密码**/
	private String passWord;

	/**是否可用**/
	private Boolean status;

	/**头像url**/
	@NotEmpty(message = "头像不能为空")
	private String headImg;

	/**用户糖果余额**/
	private Integer candyAmount;

	/**用户名**/
	@NotEmpty(message = "昵称不能为空")
	private String nickName;

	/**邀请码*/
	private String invetCode;

	/**邀请人昵称*/
	private String invertNickName;


	/**第三方登录账号*/
	@NotEmpty(message = "第三方账号ID不能为空")
	private String thirdId;

	/**第三方登录类型*/
	@NotNull(message = "第三方账号登录类型不能为空")
	private Integer thirdType;

	@ApiModelProperty(notes = "个推客户端ID",name = "个推客户端ID")
	private String gtClientId;

	public String getGtClientId() {
		return gtClientId;
	}

	public void setGtClientId(String gtClientId) {
		this.gtClientId = gtClientId;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	public Integer getThirdType() {
		return thirdType;
	}

	public void setThirdType(Integer thirdType) {
		this.thirdType = thirdType;
	}

	public String getInvertNickName() {
		return invertNickName;
	}

	public void setInvertNickName(String invertNickName) {
		this.invertNickName = invertNickName;
	}

	public String getInvetCode() {
		return invetCode;
	}

	public void setInvetCode(String invetCode) {
		this.invetCode = invetCode;
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

	public void setStatus(Boolean status){
		this.status = status;
	}

	public Boolean getStatus(){
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


}
