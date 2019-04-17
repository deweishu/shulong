package com.qkl.manage.admin.bean;


import com.qkl.common.permission.PermissionDefiner;
import com.qkl.common.permission.PermissionGroupDefiner;
import com.qkl.common.permission.PermissionType;

/**
 * Created by dengjihai on 2018/3/7.
 */
@PermissionType
public class AdminPermission {

    @PermissionGroupDefiner(name = "系统管理")
    public static final String SYSTEM_GROUP = "admin";

    @PermissionDefiner(name = "管理员管理", group = SYSTEM_GROUP)
    public static final String ADMIN = "admin";

    @PermissionDefiner(name = "角色管理", group = SYSTEM_GROUP)
    public static final String ROLE = "role";

    @PermissionDefiner(name = "系统操作日志", group = SYSTEM_GROUP)
    public static final String SYS_LOG_LIST = "sys_log_list";

    @PermissionDefiner(name = "系统配置", group = SYSTEM_GROUP)
    public static final String SYS_CONFIG = "sys_config";

    @PermissionDefiner(name = "短信发送记录", group = SYSTEM_GROUP)
    public static final String SMS_RECORD = "sms_record_list";

    @PermissionDefiner(name = "热搜关键词维护", group = SYSTEM_GROUP)
    public static final String HOT_SEARCH_LIST = "hot_search_list";

    @PermissionDefiner(name = "APP版本管理", group = SYSTEM_GROUP)
    public static final String APP_MANAGE_VERSION = "app_version_manage";


    @PermissionGroupDefiner(name = "会员管理")
    public static final String MEMBER_GROUP = "member";

    @PermissionDefiner(name = "会员信息", group = MEMBER_GROUP)
    public static final String MEMBER = "member_list";

    @PermissionDefiner(name = "用户糖果记录", group = MEMBER_GROUP)
    public static final String MEMBER_CADNY_LOG = "member_candy_log";

    @PermissionDefiner(name = "用户签到记录", group = MEMBER_GROUP)
    public static final String MEMBER_SIGN_LOG = "member_sign_log";

    @PermissionDefiner(name = "用户邀请记录", group = MEMBER_GROUP)
    public static final String MEMBER_SHARE_LOG = "member_share_log";


    @PermissionDefiner(name = "建议反馈", group = MEMBER_GROUP)
    public static final String MEMBER_REQUESTION = "member_requestion";



    @PermissionGroupDefiner(name = "合作商管理")
    public static final String PARTNER_GROUP = "partner";

    @PermissionDefiner(name = "合作商信息", group = PARTNER_GROUP)
    public static final String PARTNER_LIST = "partner_list";

    @PermissionDefiner(name = "合作商重置密码", group = PARTNER_GROUP)
    public static final String PARTNER_REST_PWD = "partner_rest_pwd";


    @PermissionGroupDefiner(name = "我的应用")
    public static final String APP_MARKET_GROUP = "app_market";


    @PermissionDefiner(name = "APP版本审核", group = APP_MARKET_GROUP)
    public static final String APP_AUDIT = "audit_app";


    @PermissionDefiner(name = "我的应用", group = APP_MARKET_GROUP)
    public static final String MY_APP_LIST = "sys_app_list";

    @PermissionDefiner(name = "应用版本查看", group = APP_MARKET_GROUP)
    public static final String APP_VERSION = "app_version";

    @PermissionDefiner(name = "发布/更新版本信息", group = APP_MARKET_GROUP)
    public static final String APP_VERSION_UPDATE = "app_version_update";

    @PermissionDefiner(name = "删除APP版本", group = APP_MARKET_GROUP)
    public static final String APP_VERSION_DELETE = "app_version_delete";

    @PermissionGroupDefiner(name = "系统应用管理")
    public static final String SYS_APP_MARKET_GROUP = "sys_app_market";

    @PermissionDefiner(name = "分类推荐", group = SYS_APP_MARKET_GROUP)
    public static final String CATE_RECOMMED_APP = "cate_recommed_app";

    @PermissionDefiner(name = "今日主题", group = SYS_APP_MARKET_GROUP)
    public static final String TODAY_APP = "today_app";

    @PermissionDefiner(name = "首页APP维护", group = SYS_APP_MARKET_GROUP)
    public static final String INDEX_APP = "index_app";

    @PermissionDefiner(name = "应用榜", group = SYS_APP_MARKET_GROUP)
    public static final String APP_SORT = "app_sort";

    @PermissionDefiner(name = "游戏榜", group = SYS_APP_MARKET_GROUP)
    public static final String GAME_SORT = "game_sort";

    @PermissionDefiner(name = "系统所有应用", group = SYS_APP_MARKET_GROUP)
    public static final String SYS_ALL_APP = "sys_all_app";

    @PermissionDefiner(name = "审核APP/上下架APP", group = SYS_APP_MARKET_GROUP)
    public static final String SHENHE_APP = "shenhe_app";

    @PermissionDefiner(name = "全局应用的版本控制", group = SYS_APP_MARKET_GROUP)
    public static final String SYS_APP_VERSION = "sys_app_version";

    @PermissionDefiner(name = "审核APP/上下架APP版本", group = SYS_APP_MARKET_GROUP)
    public static final String SHENHE_DOWN_UP_VERSION = "shenhe_down_up_version";

    @PermissionDefiner(name = "应用分类维护", group = SYS_APP_MARKET_GROUP)
    public static final String APP_CATEGORY_LIST = "app_category_list";

    @PermissionDefiner(name = "应用专题操作", group = SYS_APP_MARKET_GROUP)
    public static final String APP_CATE_ADD = "app_cate_add";

    @PermissionDefiner(name = "应用评论管理", group = SYS_APP_MARKET_GROUP)
    public static final String APP_COMMENT_LIST = "app_comment_list";


    // 这个是一级菜单，是权限组
    @PermissionGroupDefiner(name = "钱包管理")
    public static final String WALLET = "wallet";

    //这个是二级菜单，是具体的某一个菜单权限。 模仿都模仿的不到位。
    @PermissionDefiner(name = "钱包管理", group = WALLET)
    public static final String WALLET_LIST = "wallet_list";







    @PermissionGroupDefiner(name = "广告活动管理")
    public static final String ADVERT_ACTIVITY_GROUP = "advert_activity";


    @PermissionDefiner(name = "首页广告维护", group = ADVERT_ACTIVITY_GROUP)
    public static final String INDEX_ADVERT_LIST = "index_advert_list";

    @PermissionDefiner(name = "添加/更新首页广告", group = ADVERT_ACTIVITY_GROUP)
    public static final String UPDATE_INDEX_ADVERT = "update_index_advert";

    @PermissionDefiner(name = "删除首页广告", group = ADVERT_ACTIVITY_GROUP)
    public static final String DELETE_INDEX_ADVERT = "delete_index_advert";

    @PermissionDefiner(name = "活动管理", group = ADVERT_ACTIVITY_GROUP)
    public static final String ACTIVITY_LIST = "activity_list";

    @PermissionDefiner(name = "添加/更新活动信息", group = ADVERT_ACTIVITY_GROUP)
    public static final String UPDATE_ACTIVITY_INFO = "update_activity_info";

    @PermissionGroupDefiner(name = "APP推送管理")
    public static final String MSG_PUSH = "msg_push";

    @PermissionDefiner(name = "发送消息推送", group = MSG_PUSH)
    public static final String SEND_MSG = "send_msg";

    @PermissionDefiner(name = "查看推送记录", group = MSG_PUSH)
    public static final String PUSH_MSG_LOG = "push_msg_log";


    /*@PermissionDefiner(name = "版本管理", group = SYSTEM_GROUP)
    public static final String APP_VERSION = "appversion";

    @PermissionDefiner(name = "系统状态", group = SYSTEM_GROUP)
    public static final String SYS_STATUS = "sys_status";

    @PermissionDefiner(name = "系统字典", group = SYSTEM_GROUP)
    public static final String SYS_DICT = "sys_dict";

    @PermissionDefiner(name = "系统参数", group = SYSTEM_GROUP)
    public static final String SYS_PARAM = "sys_param";*/

    // others...
}
