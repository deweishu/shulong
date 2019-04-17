package com.qkl.admin.entity;


import com.qkl.common.bean.UUIDEntity;
import javax.persistence.*;


/**
 * 
 * 系统客户表
 * 
 **/
@Entity
@Table(name = "sys_customer")
public class Customer extends UUIDEntity {

	/**客户名称**/
	@Column(name = "name")
	private String name;

	/**手机号**/
	@Column(name = "phone")
	private String phone;

	/**客户状态(1.正常0.封禁)**/
	@Column(name = "status")
	private Boolean status;



	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setStatus(Boolean status){
		this.status = status;
	}

	public Boolean getStatus(){
		return this.status;
	}

}
