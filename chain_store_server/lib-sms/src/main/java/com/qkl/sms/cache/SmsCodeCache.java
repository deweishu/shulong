package com.qkl.sms.cache;

import com.qkl.common.cache.AbstractCache;
import com.qkl.common.cache.CacheConstant;
import com.qkl.common.util.JsonUtil;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.TimeUtil;
import com.qkl.sms.dto.SmsCacheDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by dengjihai on 2018/3/2.
 */
@Component
public class SmsCodeCache extends AbstractCache<String> {

    public static final Logger logger = LoggerFactory.getLogger(SmsCodeCache.class);

    /**
     * 通过手机号和code，验证验证码是否可用
     * @param mobile
     * @param smsCode
     * @return
     */
    public  boolean codeIsValid(String mobile,String smsCode){
        SmsCacheDto smsCacheDto = getCacheSms(mobile);
        if(smsCacheDto==null){
            return  false;
        }
        //不能超过5mins 并且验证码正确
        if (getSmsMins(smsCacheDto)<=5 && smsCacheDto.getSmsCode().equals(smsCode)){
            remove(mobile); // 移除掉
            return true;
        }
        return  false;
    }

    /**
     *  保存发送的验证码
     * @param mobile
     */
    public String saveSmsCode(String mobile,String sendCode){
        //  生成验证码code值
        SmsCacheDto smsCacheDto = new SmsCacheDto();
        smsCacheDto.setSmsCode(sendCode);
        smsCacheDto.setSendTime(TimeUtil.now());
        //缓存验证码信息
        SmsCacheDto cacheDto =this.getCacheSms(mobile);
        if(cacheDto!=null ){
            smsCacheDto.setSmsCode(sendCode);
            //移除之前的 ,重新保存
            invalid(mobile);
        }
        String saveJson = JsonUtil.beanToJson(smsCacheDto);
        logger.info("\n 最新缓存信息："+ saveJson);
        put(mobile, saveJson);
        return  smsCacheDto.getSmsCode();
    }

    /**
     * 获取缓存的短信信息
     * @param mobile
     * @return
     */
    public SmsCacheDto getCacheSms(String mobile){
        //获取缓存信息
        String codeCache=get(mobile);
        //不存在，返回null
        if(StringUtil.isNil(codeCache)){
            return  null;
        }
        // 对象转换
        SmsCacheDto smsCacheDto = JsonUtil.jsonToBean(codeCache,SmsCacheDto.class);
        return smsCacheDto;
    }

    /**
     * 得到上一次的时间
     * @param smsCacheDto
     * @return
     */
    long getSmsMins(SmsCacheDto smsCacheDto){
        Date sendDate= smsCacheDto.getSendTime();
        Date nowDate = TimeUtil.now();
        long min = TimeUtil.getDistanceTime(sendDate,nowDate);
        return  min;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }

    @Override
    protected String getPrefix() {
        return CacheConstant.SMS_CODE_CACHE;
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.MINUTE*5;
    }

}
