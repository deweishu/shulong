package com.dwshu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dwshu
 * @create 2020/4/20
 * @describe
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoodsDto {
    private String img;
    private String price;
    private String name;
    private String title;
    private String comment;
    private String shopName;

}
