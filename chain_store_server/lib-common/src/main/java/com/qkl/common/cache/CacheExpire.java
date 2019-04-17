package com.qkl.common.cache;

import java.util.concurrent.TimeUnit;

/**
 * 缓存到期
 * Created by dengjihai on 2018/3/28.
 */

public class CacheExpire {

    public static final CacheExpire NO_EXPIRE = null;

    public static final CacheExpire ONE_MINUTE = new CacheExpire(1,TimeUnit.MINUTES);

    public static final CacheExpire FIVE_MINUTE = new CacheExpire(5,TimeUnit.MINUTES);

    public static final CacheExpire TEN_MINUTE = new CacheExpire(10,TimeUnit.MINUTES);

    public static final CacheExpire HALF_HOUR = new CacheExpire(30,TimeUnit.MINUTES);

    public static final CacheExpire ONE_HOUR = new CacheExpire(1,TimeUnit.HOURS);

    public static final CacheExpire TWO_HOUR = new CacheExpire(2,TimeUnit.HOURS);

    public static final CacheExpire THREE_HOUR = new CacheExpire(3,TimeUnit.HOURS);

    public static final CacheExpire QUARTER_HOUR = new CacheExpire(6,TimeUnit.HOURS);

    public static final CacheExpire HALF_DAY = new CacheExpire(12,TimeUnit.HOURS);

    public static final CacheExpire ONE_DAY = new CacheExpire(1,TimeUnit.DAYS);

    public static final CacheExpire TWO_DAY = new CacheExpire(2,TimeUnit.DAYS);

    public static final CacheExpire THREE_DAY = new CacheExpire(2,TimeUnit.DAYS);

    public static final CacheExpire ONE_WEEK = new CacheExpire(7,TimeUnit.DAYS);

    public static final CacheExpire ONE_MONTH = new CacheExpire(30,TimeUnit.DAYS);

    private int duration;

    private TimeUnit timeUnit;

    private ExpireType expireType = ExpireType.AFTER_WRITE;

    public CacheExpire(int duration, TimeUnit timeUnit) {
        this.duration = duration;
        this.timeUnit = timeUnit;
    }

    public CacheExpire(int duration, TimeUnit timeUnit, ExpireType expireType) {
        this.duration = duration;
        this.timeUnit = timeUnit;
        this.expireType = expireType;
    }

    public int getDuration() {
        return duration;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public ExpireType getExpireType() {
        return expireType;
    }

    public enum ExpireType {
        AFTER_WRITE,
        AFTER_READ
    }
}
