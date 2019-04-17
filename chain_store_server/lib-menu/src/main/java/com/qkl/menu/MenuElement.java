package com.qkl.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 菜单条目
 */
public class MenuElement implements Comparable<MenuElement>, Serializable {
    private String name;

    private String url;

    private Menu menu;

    private int weight;

    public MenuElement() {
    }

    public MenuElement(String name, String url, Menu menu, int weight) {
        this.name = name;
        this.url = url;
        this.menu = menu;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonIgnore
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(MenuElement o) {
        // 权重比较为相反值，权重越大优先级越高
        return -(this.weight - o.getWeight());
    }
}
