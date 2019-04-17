package com.qkl.user.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;
import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.NotEmpty;


@ApiModel(description = "提交反馈请求实体类")
public class FeedbackDto  extends Dto {

	/**主键**/
	private String id;

	/**创建时间**/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;

	/**更新时间**/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;

	/**反馈内容**/
	@NotEmpty(message = "反馈内容不能为空")
	private String describe;

	/**联系方式**/
	@NotEmpty(message = "联系方式不能为空")
	private String phone;

	/**用户id**/
	private String userId;

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

}
