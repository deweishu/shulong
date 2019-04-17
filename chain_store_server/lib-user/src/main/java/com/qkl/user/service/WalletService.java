package com.qkl.user.service;

import com.qkl.apk.jpa.ApkRepository;
import com.qkl.common.util.StringUtil;
import com.qkl.user.entity.Wallet;
import com.qkl.user.jpa.UserRepository;
import com.qkl.user.jpa.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


/**
 * 用户操作自己的钱包
 * wjj
 * 2018年12月31日10:34:23
 */

@Service
public class WalletService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	ApkRepository apkRepository;

	/**
	 * 保存钱包数额
	 * @param wallet
	 */
	@Transactional(rollbackFor = Exception.class)
	public void save(Wallet wallet){
		System.out.println("保存了数额");
	}



    /**
     * 根据用户自己id和钱包类型 查找余额
     * @param userId
     * @param index
     * @return
     */
    public String findWallet(String userId, String index) {

        //非空判断
        Assert.isTrue(StringUtil.isNotBlank(userId), "ID不能为空");
        Assert.isTrue(StringUtil.isNotBlank(index), "ID不能为空");

        //调用持久层处理
        return walletRepository.findWalletBy2Id(userId, index);
    }
}
