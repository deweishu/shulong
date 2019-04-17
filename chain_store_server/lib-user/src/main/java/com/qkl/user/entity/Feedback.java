package com.qkl.user.entity;


import com.qkl.common.bean.UUIDEntity;
import javax.persistence.*;


/**
 * 
 * 反馈信息表
 * 
 **/
@Entity
@Table(name = "u_feedback")
public class Feedback extends UUIDEntity {

	/**反馈内容**/
	@Column(name = "feed_content")
	private String describe;

	/**联系方式**/
	@Column(name = "phone")
	private String phone;

	/**用户id**/
	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;



	public void setDescribe(String describe){
		this.describe = describe;
	}

	public String getDescribe(){
		return this.describe;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
