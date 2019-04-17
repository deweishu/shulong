package com.qkl.api.user.cache;

import com.qkl.common.cache.AbstractCache;
import com.qkl.common.cache.CacheConstant;
import com.qkl.common.util.JsonUtil;
import com.qkl.common.util.StringUtil;
import com.qkl.common.web.AppException;
import com.qkl.user.dto.TokenSessionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 用户Token会话信息缓存管理
 * Created by dengjihai on 2018/3/1.
 */
@Component
public class TokenSessionCache extends AbstractCache<String> {

    private final Logger logger = LoggerFactory.getLogger(TokenSessionCache.class);

    /**
     * 当前线程的token
     */
    private ThreadLocal<String> tokens = new ThreadLocal<>();

    /**
     * 当前线程的TokenSession信息
     */
    private final ThreadLocal<TokenSessionDto> threadUserToken = new ThreadLocal<>();


    public String getUserId(){
        String userId = (null != getUserToken() && null != getUserToken().getUserLoginResp()) ? getUserToken().getUserLoginResp().getId() : null;
        if (StringUtil.isNil(userId)) throw new AppException("用户ID为空");
        return userId;
    }


    /**
     * save userSession
     * @param token
     * @param tokenSessionDto
     */
    public void putTokenSession(String token, TokenSessionDto tokenSessionDto) {
        if (StringUtil.isNotNil(token) && Objects.nonNull(tokenSessionDto)) {
            put(token, JsonUtil.beanToJson(tokenSessionDto));
            setUserToken(tokenSessionDto);
        }
    }

    /**
     * get userSession
     * @param token
     * @return
     */
    public TokenSessionDto getTokenSession(String token){
        if(StringUtil.isBlank(token)) return null;
        // find by cache
        String str = get(token);
        if(StringUtil.isBlank(str)) return null;
        return JsonUtil.jsonToBean(str,TokenSessionDto.class);
    }

    /**
     * 设置当前线程Token信息
     * @param userToken
     */
    public void setUserToken(TokenSessionDto userToken) {
        threadUserToken.set(userToken);
    }

    /**
     * 获取当前线程Token信息
     * @return
     */
    public TokenSessionDto getUserToken() {
        return threadUserToken.get();
    }


    /**
     * 校验token值
     * 以及将有效的Token信息存储到ThreadLocal中
     * @param token
     * @return true为有效，false为无效或失效
     */
    public boolean checkToken(String token){
        TokenSessionDto tokenSessionDto = this.getTokenSession(token);
        if (Objects.isNull(tokenSessionDto)) {
            return false;
        }

        setToken(token);
        setUserToken(tokenSessionDto);
        return true;
    }


    /**
     * 清空当前线程的数据
     */
    public void clearLocal(){
        setToken(null);
        setUserToken(null);
    }

    public void setToken(String currentToken){
        tokens.set(currentToken);
    }

    public String getToken(){
        return tokens.get();
    }


    @Override
    public void invalid(String key) {
        logger.info("清除token[{}]对应的session数据", key);
        remove(key);
        clearLocal();
    }

    @Override
    protected String getPrefix() {
        return CacheConstant.TOKEN_SESSION_CACHE;
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.YEAR;
    }

}
