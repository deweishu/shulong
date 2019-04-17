//package com.qkl.user.dto;
//
//
//import com.alibaba.fastjson.annotation.JSONField;
//import com.qkl.common.dto.Dto;
//import io.swagger.annotations.ApiModelProperty;
//
//import javax.validation.constraints.NotNull;
//import java.util.Date;
//
///**
// * 暂不使用
// */
//public class WalletDto extends Dto {
//
//	/**主键**/
//	private String id;
//
//	/**钱包种类**/
//	private String type;
//
//	/**描述**/
//	private String desc;
//
//	/**创建时间**/
//	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
//	private java.util.Date createTime;
//
//	/**更新时间**/
//	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
//	private java.util.Date updateTime;
//
//	/**用户id**/
//	private String userId;
//
//	/**改变时钱包余额数**/
//	private Integer walletNum;
//
//	/**钱包发生改变的原因描述**/
//	private String changeDesc;
//
//	/**改变类型**/
//	@ApiModelProperty(notes = "钱包余额变动：***",name = "改变理由：***")
//	@NotNull(message = "发生类型不能为空")
//	private Integer changeType;
//
//	/**对应的对象id**/
//	private String changeId;
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public String getDesc() {
//		return desc;
//	}
//
//	public void setDesc(String desc) {
//		this.desc = desc;
//	}
//
//	public Date getCreateTime() {
//		return createTime;
//	}
//
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
//
//	public Date getUpdateTime() {
//		return updateTime;
//	}
//
//	public void setUpdateTime(Date updateTime) {
//		this.updateTime = updateTime;
//	}
//
//	public String getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
//
//	public Integer getWalletNum() {
//		return walletNum;
//	}
//
//	public void setWalletNum(Integer walletNum) {
//		this.walletNum = walletNum;
//	}
//
//	public String getChangeDesc() {
//		return changeDesc;
//	}
//
//	public void setChangeDesc(String changeDesc) {
//		this.changeDesc = changeDesc;
//	}
//
//	public Integer getChangeType() {
//		return changeType;
//	}
//
//	public void setChangeType(Integer changeType) {
//		this.changeType = changeType;
//	}
//
//	public String getChangeId() {
//		return changeId;
//	}
//
//	public void setChangeId(String changeId) {
//		this.changeId = changeId;
//	}
//}
