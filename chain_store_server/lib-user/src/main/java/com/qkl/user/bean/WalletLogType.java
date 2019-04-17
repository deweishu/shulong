package com.qkl.user.bean;

import com.qkl.common.bean.JpaProperty;

/**
 * 钱包记录明细
 * wjj
 */


public class WalletLogType extends JpaProperty {
    public static final WalletLogType CANDY_EXCHANGE = newInstance(WalletLogType.class, 10, "糖果兑换钱包金额");
    public static final WalletLogType RECHARGE_ADD = newInstance(WalletLogType.class, 20, "充值增加");
}
