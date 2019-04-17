package com.qkl.user.entity;


import com.qkl.common.bean.UUIDEntity;
import com.qkl.user.bean.CandyLogType;

import javax.persistence.*;


/**
 * 
 * 用户糖果记录
 * 
 **/
@Entity
@Table(name = "u_candy_log")
public class CandyLog extends UUIDEntity {

	/**用户id**/
	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;

	/**发生糖果余额数**/
	@Column(name = "candy_num")
	private Integer candyNum;

	/**糖果发生改变的原因描述**/
	@Column(name = "change_desc")
	private String changeDesc;

	/**10.邀请好友20.应用分享30.下载APP40.抽奖所得**/
	@Column(name = "change_type")
	private CandyLogType changeType;

	/**对应的对象id**/
	@Column(name = "change_id")
	private String changeId;

	/**发生日期*/
	@Column(name = "change_date")
	private String changeDate;

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public void setChangeType(CandyLogType changeType){
		this.changeType = changeType;
	}

	public CandyLogType getChangeType(){
		return this.changeType;
	}

	public void setChangeId(String changeId){
		this.changeId = changeId;
	}

	public String getChangeId(){
		return this.changeId;
	}

}
