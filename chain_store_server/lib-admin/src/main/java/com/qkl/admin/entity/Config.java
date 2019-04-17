package com.qkl.admin.entity;


import com.qkl.common.bean.UUIDEntity;
import javax.persistence.*;


/**
 * 
 * 系统配置信息表
 * 
 **/
@Entity
@Table(name = "sys_config")
public class Config extends UUIDEntity {

	/**配置code**/
	@Column(name = "config_code")
	private String configCode;

	/**配置描述**/
	@Column(name = "config_desc")
	private String configDesc;

	/**配置内容**/
	@Column(name = "config_content")
	private String configContent;

	/**配置数值**/
	@Column(name = "config_num")
	private Integer configNum;



	public void setConfigCode(String configCode){
		this.configCode = configCode;
	}

	public String getConfigCode(){
		return this.configCode;
	}

	public void setConfigDesc(String configDesc){
		this.configDesc = configDesc;
	}

	public String getConfigDesc(){
		return this.configDesc;
	}

	public void setConfigContent(String configContent){
		this.configContent = configContent;
	}

	public String getConfigContent(){
		return this.configContent;
	}

	public void setConfigNum(Integer configNum){
		this.configNum = configNum;
	}

	public Integer getConfigNum(){
		return this.configNum;
	}

}
