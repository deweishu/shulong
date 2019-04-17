package com.qkl.common.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形的简单dto
 * Created by dengjihai on 2018/3/28.
 */
public class TreeSimpleDto {

    private String id;
    private String name;
    private List<TreeSimpleDto> children = new ArrayList<>();

    public TreeSimpleDto() {
    }

    public TreeSimpleDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeSimpleDto> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSimpleDto> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeSimpleDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}
