package com.qkl.user.entity;


import com.qkl.apk.entity.Apk;
import com.qkl.common.bean.UUIDEntity;
import javax.persistence.*;


/**
 * 
 * 用户评论表
 * 
 **/
@Entity
@Table(name = "u_comment_log")
public class CommentLog extends UUIDEntity {

	/**评分数（1-5）**/
	@Column(name = "comment_num")
	private Integer commentNum;

	/**评分内容**/
	@Column(name = "comment_desc")
	private String commentDesc;

	/**用户id**/
	@JoinColumn(name = "user_id")
    @ManyToOne
	private User user;

	/**手机型号**/
	@Column(name = "mobile_type")
	private String mobileType;

	@JoinColumn(name = "apk_id")
    @ManyToOne
	private Apk apk;


	@Column(name = "love_num")
	private Integer loveNum;//点赞数

	@Column(name = "no_num")
	private Integer noNum;//踩数

	public Integer getNoNum() {
		return noNum==null?0:noNum;
	}

	public void setNoNum(Integer noNum) {
		this.noNum = noNum;
	}

	public Integer getLoveNum() {
		return loveNum==null?0:loveNum;
	}

	public void setLoveNum(Integer loveNum) {
		this.loveNum = loveNum;
	}

	public Apk getApk() {
		return apk;
	}

	public void setApk(Apk apk) {
		this.apk = apk;
	}

	public void setCommentNum(Integer commentNum){
		this.commentNum = commentNum;
	}

	public Integer getCommentNum(){
		return this.commentNum;
	}

	public void setCommentDesc(String commentDesc){
		this.commentDesc = commentDesc;
	}

	public String getCommentDesc(){
		return this.commentDesc;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMobileType(String mobileType){
		this.mobileType = mobileType;
	}

	public String getMobileType(){
		return this.mobileType;
	}

}
