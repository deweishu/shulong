package com.qkl.user.entity;


import com.qkl.common.bean.UUIDEntity;
import javax.persistence.*;


/**
 * 
 * 签到记录表
 * 
 **/
@Entity
@Table(name = "u_sign_log")
public class SignLog extends UUIDEntity {

	/**用户id**/
	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;

	/**签到日期**/
	@Column(name = "sign_date")
	private String signDate;

	/**签到获得的糖果数**/
	@Column(name = "candy_num")
	private Integer candyNum;


	/**签到ip地址*/
	@Column(name = "sign_ip")
	private String signIp;


	public String getSignIp() {
		return signIp;
	}

	public void setSignIp(String signIp) {
		this.signIp = signIp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
