package com.qkl.user.entity;


import com.qkl.common.bean.UUIDEntity;
import com.qkl.user.bean.WalletLogType;

import javax.persistence.*;


/**
 *
 * 用户钱包记录
 * wjj
 **/
@Entity
@Table(name = "u_wallet_balance")
public class Wallet extends UUIDEntity {

	/**用户id**/
	@JoinColumn(name = "user_id")
	@OneToMany
	private User user;

	/**钱包地址**/
    @Column(name = "address")
    private String walletAddress;

	/**钱包余额数**/
	@Column(name = "wallet_balance")
	private Double walletBalance;

	/**钱包类型**/
	@Column(name = "wallet_type")
	private String walletType;

	/**钱包描述**/
	@Column(name = "wallet_desc")
	private String walletDesc;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public Double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(Double walletBalance) {
		this.walletBalance = walletBalance;
	}

	public String getWalletType() {
		return walletType;
	}

	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}

	public String getWalletDesc() {
		return walletDesc;
	}

	public void setWalletDesc(String walletDesc) {
		this.walletDesc = walletDesc;
	}
}
