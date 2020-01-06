package com.dwshu.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author dwshu
 * @create 2020/1/6
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class WechatBase {

    public String appId;          //应用唯一标识，在微信开放平台提交应用审核通过后获得
    public String appSecret;      //应用密钥
}
