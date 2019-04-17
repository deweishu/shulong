package com.qkl.menu;

import java.io.Serializable;

public class Menu implements Comparable<Menu>, Serializable {

    private String code;

    private String name;

    private String icon;

    private int priority;

    public Menu() {
    }

    public Menu(String code, String name, String icon, int priority) {
        this.code = code;
        this.name = name;
        this.icon = icon;
        this.priority = priority;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Menu o) {
        return this.priority - o.getPriority();
    }
}
