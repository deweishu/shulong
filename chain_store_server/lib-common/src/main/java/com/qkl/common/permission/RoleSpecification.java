package com.qkl.common.permission;

/**
 * 角色规格
 * 通过此规格限制是否能修改角色的权限
 */
public enum RoleSpecification {

    EMLOYEE,    //普通员工角色
    CASHIER,    //出纳角色
    MANAGER,    //管理员角色
    MODIFIABLE,  //可修改的角色

}
