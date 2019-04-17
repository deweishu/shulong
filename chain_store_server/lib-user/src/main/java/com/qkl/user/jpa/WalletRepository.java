package com.qkl.user.jpa;


import com.qkl.user.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 钱包接口
 * wjj
 */

public interface WalletRepository extends JpaRepository<Double, String>, JpaSpecificationExecutor<Wallet> {

    /**
     * 根据钱包id查询钱包余额
     * @param wallet_id
     * @return
     */
    public Double findOne(String wallet_id);

    /**
     * 根据用户id和钱包类型 查找 自己余额
     * @param userId
     * @param index
     * @return
     */
    public String findWalletBy2Id(String userId, String index);//未完
}
