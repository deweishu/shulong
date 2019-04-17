package com.qkl.menu;

import java.util.List;

public class UserMenuListWrapper {

    private List<UserMenu> userMenus;

    public UserMenuListWrapper() {
    }

    public UserMenuListWrapper(List<UserMenu> userMenus) {
        this.userMenus = userMenus;
    }

    public List<UserMenu> getUserMenus() {
        return userMenus;
    }

    public void setUserMenus(List<UserMenu> userMenus) {
        this.userMenus = userMenus;
    }
}
