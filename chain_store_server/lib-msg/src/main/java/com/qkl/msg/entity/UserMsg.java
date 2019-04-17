package com.qkl.msg.entity;


import com.qkl.common.bean.UUIDEntity;
import com.qkl.user.entity.User;

import javax.persistence.*;


/**
 * 
 * 用户消息表
 * 
 **/
@Entity
@Table(name = "m_user_msg")
public class UserMsg extends UUIDEntity {

	/**用户id**/
	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;

	/**消息标题**/
	@Column(name = "msg_title")
	private String msgTitle;

	/**消息内容**/
	@Column(name = "msg_content")
	private String msgContent;

	/**阅读状态(1未读0已读)**/
	@Column(name = "read_status")
	private Boolean readStatus;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMsgTitle(String msgTitle){
		this.msgTitle = msgTitle;
	}

	public String getMsgTitle(){
		return this.msgTitle;
	}

	public void setMsgContent(String msgContent){
		this.msgContent = msgContent;
	}

	public String getMsgContent(){
		return this.msgContent;
	}

	public void setReadStatus(Boolean readStatus){
		this.readStatus = readStatus;
	}

	public Boolean getReadStatus(){
		return this.readStatus;
	}

}
