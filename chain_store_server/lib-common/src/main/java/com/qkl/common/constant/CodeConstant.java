package com.qkl.common.constant;

/**
 * 常量类
 * Created by dengjihai on 2017/10/30.
 */
public class CodeConstant {

    /**
     * 默认CODE，如：验证码和密码
     */
    public static final String DEFAULT_CODE = "6666";

    /**合作商的角色id*/
    public static final String PARTNER_ROLE_ID="100002";

    /**合作商初始密码*/
    public static final String PARTNER_DEFAULT_PASS="123456";

    // 累计签到7天，获取的抽奖资格
    public static final Integer DAY_NUM=7;

    /**apk-customer-id，系统用户，固定为10000*/
    public static final String SYS_CUSTOMER_ID="10000";

    // 区块链应用分类id，
    public static final String CHAIN_STORE_APP_CATE_ID="7";

    // 区块链应用分类id，游戏
    public static final String CHAIN_STORE_APP_CATE_ID_GAME="12";


    /**
     * 今日主题最多app个数
     */
    public static final Integer INNDEX_APP_LIST_SIZE=3;

    /**
     * 今日主题最多app个数
     */
    public static final Integer TODAY_APP_LIST_SIZE=4;

    /**用户的邀请码长度*/
    public static final Integer INVET_CODE_LENGTH=6;

    /**生成邀请码，最多失败多少次*/
    public static final Integer INVET_MAX_NUM=100;

}
