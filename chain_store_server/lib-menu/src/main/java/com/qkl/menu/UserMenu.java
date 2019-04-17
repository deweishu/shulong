package com.qkl.menu;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;


public class UserMenu implements Comparable<UserMenu>, Serializable {

    private Menu menu;

    private List<MenuElement> menuElements = Lists.newArrayList();

    public UserMenu() {
    }

    public UserMenu(Menu menu) {
        this.menu = menu;
    }

    public void addElement(MenuElement menuElement) {
        menuElements.add(menuElement);
    }

    public List<MenuElement> getMenuElements() {
        return menuElements;
    }

    public void setMenuElements(List<MenuElement> menuElements) {
        this.menuElements = menuElements;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public int compareTo(UserMenu o) {
        return menu.compareTo(o.getMenu());
    }
}
