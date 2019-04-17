package com.qkl.common.bean.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by dengjihai on 2018/3/28.
 */
@Component
@ConfigurationProperties(prefix="spring.oss")
public class OSSConfig {

    private String accessId;
    private String accessKey;
    private String endpoint;
    private String bucket;
    /**
     * policy 的有效期，单位秒
     */
    private long policyExpireTime;

    private String dir;

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public long getPolicyExpireTime() {
        return policyExpireTime;
    }

    public void setPolicyExpireTime(long policyExpireTime) {
        this.policyExpireTime = policyExpireTime;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
