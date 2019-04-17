package com.qkl.admin.entity;


import com.qkl.common.bean.UUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 操作日志
 * 
 **/

@Entity
@Table(name = "pub_oper_log")
public class PubOperLog extends UUIDEntity {

	/**操作app模块**/
	@Column(name = "module")
	private String module;

	/**操作者id**/
	@Column(name = "operator_id")
	private String operatorId;

	/**请求方法**/
	@Column(name = "method")
	private String method;

	/**请求路径**/
	@Column(name = "uri")
	private String uri;

	/**请求参数**/
	@Column(name = "parameter")
	private String parameter;


	@Column(name = "operate_name")
	private String operateName;

	@Column(name = "operate_phone")
	private String operatePhone;

	@Column(name = "operate_content")
	private String operateContent;

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOperatePhone() {
		return operatePhone;
	}

	public void setOperatePhone(String operatePhone) {
		this.operatePhone = operatePhone;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public void setModule(String module){
		this.module = module;
	}

	public String getModule(){
		return this.module;
	}

	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}

	public String getOperatorId(){
		return this.operatorId;
	}

	public void setMethod(String method){
		this.method = method;
	}

	public String getMethod(){
		return this.method;
	}

	public void setUri(String uri){
		this.uri = uri;
	}

	public String getUri(){
		return this.uri;
	}

	public void setParameter(String parameter){
		this.parameter = parameter;
	}

	public String getParameter(){
		return this.parameter;
	}

}
