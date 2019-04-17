package com.qkl.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 简单的dto
 * Created by dengjihai on 2018/3/28.
 */
@ApiModel(value = "响应数据")
public class SimpleDto extends Dto implements ILoad{

    @ApiModelProperty(name = "编号ID")
    private String id;
    @ApiModelProperty(name = "名称")
    private String name;

    public SimpleDto() {
        super();
    }

    public SimpleDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public SimpleDto load(Object[] o) {
        return new SimpleDto(blankWhenNull(o[0]),blankWhenNull(o[1]));
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

    @Override
    public String toString() {
        return "SimpleDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
