package com.qkl.user.entity;


import com.qkl.common.bean.UUIDEntity;
import com.qkl.user.bean.ThirdLoginType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;


/**
 * 
 * 用户信息表
 * 
 **/
@Entity
@Table(name = "u_user")
public class User extends UUIDEntity {

	/**手机号**/
	@Column(name = "mobile")
	private String mobile;

	/**登录密码**/
	@Column(name = "pass_word")
	private String passWord;

	/**是否可用**/
	@Column(name = "status")
	private Boolean status;

	/**头像url**/
	@Column(name = "head_img")
	private String headImg;

	/**用户糖果余额**/
	@Column(name = "candy_amount")
	private Integer candyAmount;

	/**用户名**/
	@Column(name = "nick_name")
	private String nickName;


	/**自己的邀请码*/
	@Column(name = "invet_code")
	private String invetCode;

	/**邀请人*/
	@ManyToOne
	@JoinColumn(name = "invite_user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private User inviteUser;

	/**第三方登录账号*/
	@Column(name = "third_id")
	private String thirdId;

	/**第三方登录类型*/
	@Column(name = "third_type")
	private ThirdLoginType thridType;

	/**个推客户端id*/
	@Column(name = "gt_client_id")
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

	public ThirdLoginType getThridType() {
		return thridType;
	}

	public void setThridType(ThirdLoginType thridType) {
		this.thridType = thridType;
	}

	public User getInviteUser() {
		return inviteUser;
	}

	public void setInviteUser(User inviteUser) {
		this.inviteUser = inviteUser;
	}

	public String getInvetCode() {
		return invetCode;
	}

	public void setInvetCode(String invetCode) {
		this.invetCode = invetCode;
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
