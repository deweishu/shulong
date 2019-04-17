package com.qkl.common.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 日期时间工具
 */
public class TimeUtil {

    private static final Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    public static final String datePattern = "yyyy-MM-dd";

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(datePattern);

    public static final String dateTimePattern = "yyyy-MM-dd HH:mm:ss";

    public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat(dateTimePattern);

    private static final String milliDateTimePattern = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final DateFormat MILLI_DATETIME_FORMAT = new SimpleDateFormat(milliDateTimePattern);

    public static final String MILLI_DATETIME_STR = "yyyyMMddHHmmssSSS";

    public static final String DATETIME_STR = "yyyyMMddHHmmss";

    public static final String DATE_STR = "yyyyMMdd";

    public static final String DATE_GMT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static Date get(Integer year, Integer month, Integer day) {
        return new DateTime(year, month, day, 0, 0).toDate();
    }

    /**
     * @return 当前时间值
     */
    public static Long timestamp() {
        return DateTime.now().toDate().getTime() / 1000;
    }

    /**
     * @return 当前时间对象
     */
    public static Date now() {
        return DateTime.now().toDate();
    }

    /**
     * yyyy-MM-dd格式日期
     * @return
     */
    public static Date currentDate(){
        Date date = now();
        try {
            String dateStr = DATE_FORMAT.format(date);
            return DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            return date;
        }
    }

    /**
     * @return 当天的第一毫秒所对应的日期对象
     */
    public static Date today() {
        return firstMillisOfDay(DateTime.now());
    }

    /**
     * @return 克隆一个日期对象
     */
    public static Date clone(Date d) {
        return (new DateTime(d)).toDate();
    }

    /**
     * 以传入时间对象为基准变动年份
     *
     * @param d 基准时间
     * @param i 变化量
     * @return 变动后的时间对象
     */
    public static Date convertYear(Date d, int i) {
        d.setTime((new DateTime(d)).plusYears(i).getMillis());
        return d;
    }

    /**
     * 以传入时间对象为基准变动月份
     *
     * @param d 基准时间
     * @param i 变化量
     * @return 变动后的时间对象
     */
    public static Date convertMonth(Date d, int i) {
        d.setTime((new DateTime(d)).plusMonths(i).getMillis());
        return d;
    }

    /**
     * 以传入时间对象为基准变动日期
     *
     * @param d 基准时间
     * @param i 变化量
     * @return 变动后的时间对象
     */
    public static Date convertDay(Date d, int i) {
        d.setTime((new DateTime(d)).plusDays(i).getMillis());
        return d;
    }

    /**
     * 以传入时间对象为基准变动小时
     *
     * @param d 基准时间
     * @param i 变化量
     * @return 变动后的时间对象
     */
    public static Date convertHour(Date d, int i) {
        d.setTime((new DateTime(d)).plusHours(i).getMillis());
        return d;
    }

    /**
     * 以传入时间对象为基准变动分钟
     *
     * @param d 基准时间
     * @param i 变化量
     * @return 变动后的时间对象
     */
    public static Date convertMinute(Date d, int i) {
        d.setTime((new DateTime(d)).plusMinutes(i).getMillis());
        return d;
    }

    /**
     * 以传入时间对象为基准变动秒数
     *
     * @param d 基准时间
     * @param i 变化量
     * @return 变动后的时间对象
     */
    public static Date convertSecond(Date d, int i) {
        d.setTime((new DateTime(d)).plusSeconds(i).getMillis());
        return d;
    }

    /**
     * 以传入时间对象为基准变动毫秒
     *
     * @param d 基准时间
     * @param i 变化量
     * @return 变动后的时间对象
     */
    public static Date convertMilliSecond(Date d, int i) {
        d.setTime((new DateTime(d)).plusMillis(i).getMillis());
        return d;
    }

    /**
     * 解析日期字符串
     */
    public static Date parseDate(String strDate) {
        return DateTime.parse(strDate).toDate();
    }

    /**
     * 解析日期字符串
     * @param strDatetime
     * @return
     */
    public static Date parseDatetime(String strDatetime) {
        if (null==strDatetime || 0==strDatetime.trim().length()) {
            return null;
        }
        Date date = null;
        try {
            date = DATETIME_FORMAT.parse(strDatetime);
        } catch (ParseException e) {
            date = null;
            logger.error("异常错误堆栈信息====》" + ExceptionUtils.getFullStackTrace(e));
        }
        return date;
    }


    /**
     * 将日期对象格式化为日期标准格式的字符串
     *
     * @param date 待格式化的日期对象
     */
    public static String dateFormat(Date date) {
        return toString(date, datePattern);
    }

    /**
     * 将日期对象格式化为日期、时间标准格式的字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param date 待格式化的日期对象
     * @return 格式化为日期、时间格式的字符串
     */
    public static String dateTimeFormat(Date date) {
        return toString(date, dateTimePattern);
    }

    /**
     * 将日期对象格式化为日期、时间标准格式的字符串 yyyyMMddHHmmssSSS
     *
     * @param date 待格式化的日期对象
     * @return 格式化为日期、时间格式的字符串
     */
    public static String milliDatetimeFormat(Date date){
        return toString(date, MILLI_DATETIME_STR);
    }

    /**
     * yyyyMMddHHmmss
     */
    public static String minDatetimeFormate(Date date){
        return toString(date, DATETIME_STR);
    }

    /**
     * yyyyMMdd
     */
    public static String dateSerialFormate(Date date){
        return toString(date, DATE_STR);
    }

    /**
     * 将日期对象格式化为日期、时间标准格式的字符串 yyyyMMddHHmmssSSS
     */
    public static String getMilliDatetime(){
        return milliDatetimeFormat(now());
    }

    /**
     * yyyyMMddHHmmss
     */
    public static String getMinDatetime(){
        return minDatetimeFormate(now());
    }

    /**
     * 将日期对象格式化为日期、时间标准格式的字符串
     * @param date
     * @return
     */
    public static String dateGTMFormat(Date date){
        SimpleDateFormat df = new SimpleDateFormat(DATE_GMT_PATTERN);
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }



    /**
     * 将日期对象格式化为特定格式的字符串
     *
     * @param date    待格式化的日期对象
     * @param pattern 格式化标准
     */
    public static String toString(Date date, String pattern) {
        return new DateTime(date).toString(pattern);
    }

    /**
     * 获取一天中的第一毫秒的日期信息
     *
     * @param d 待变更的时间对象
     */
    public static Date firstMillisOfDay(Date d) {
        return firstMillisOfDay(new DateTime(d));
    }

    /**
     * @return 第二天的第一毫秒所对应的日期对象
     */
    public static Date firstMillisOfNextDay(Date d) {
        return firstMillisOfDay(new DateTime(d).plusDays(1));
    }

    /**
     * 获取一天中的第一毫秒的日期信息
     *
     * @param dt 待变更的时间对象
     */
    private static Date firstMillisOfDay(DateTime dt) {
        return dt.withTimeAtStartOfDay().toDate();
    }

    /**
     * 获取一天中的最后一毫秒的日期信息
     *
     * @param d 待变更的时间对象
     */
    public static Date lastMillisOfDay(Date d) {
        return lastMillisOfDay(new DateTime(d));
    }

    /**
     * 获取一天中的最后一毫秒的日期信息
     *
     * @param dt 待变更的时间对象
     */
    private static Date lastMillisOfDay(DateTime dt) {
        return dt.withTimeAtStartOfDay().plusDays(1).minusMillis(1).toDate();
    }

    /**
     * 获取SimpleDateFormat
     * @param parttern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {
        return new SimpleDateFormat(parttern);
    }

    /**
     * 获取日期中的某数值。如获取月份
     * @param date 日期
     * @param dateType 日期格式
     * @return 数值
     */
    private static int getInteger(Date date, int dateType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(dateType);
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     * @param date 日期字符串
     * @param dateType 类型
     * @param amount 数值
     * @return 计算后日期字符串
     */
    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = stringToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString = dateToString(myDate, dateStyle);
        }
        return dateString;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     * @param date 日期
     * @param dateType 类型
     * @param amount 数值
     * @return 计算后日期
     */
    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 获取精确的日期
     * @param timestamps 时间long集合
     * @return 日期
     */
    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<Long, long[]>();
        List<Long> absoluteValues = new ArrayList<Long>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = { timestamps.get(i), timestamps.get(j) };
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的
                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    // 如果timestamps的size为2，这是差值只有一个，因此要给默认值
                    minAbsoluteValue = absoluteValues.get(0);
                }
                for (int i = 0; i < absoluteValues.size(); i++) {
                    for (int j = i + 1; j < absoluteValues.size(); j++) {
                        if (absoluteValues.get(i) > absoluteValues.get(j)) {
                            minAbsoluteValue = absoluteValues.get(j);
                        } else {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
                    } else if (absoluteValues.size() == 1) {
                        // 当timestamps的size为2，需要与当前时间作为参照
                        long dateOne = timestampsLastTmp[0];
                        long dateTwo = timestampsLastTmp[1];
                        if ((Math.abs(dateOne - dateTwo)) < 100000000000L) {
                            timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
                        } else {
                            long now = new Date().getTime();
                            if (Math.abs(dateOne - now) <= Math.abs(dateTwo - now)) {
                                timestamp = dateOne;
                            } else {
                                timestamp = dateTwo;
                            }
                        }
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * 获取日期字符串的日期风格。失敗返回null。
     * @param date 日期字符串
     * @return 日期风格
     */
    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
        List<Long> timestamps = new ArrayList<Long>();
        for (DateStyle style : DateStyle.values()) {
            Date dateTmp = stringToDate(date, style.getValue());
            if (dateTmp != null) {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        dateStyle = map.get(getAccurateDate(timestamps).getTime());
        return dateStyle;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     * @param date 日期字符串
     * @return 日期
     */
    public static Date stringToDate(String date) {
        DateStyle dateStyle = null;
        return stringToDate(date, dateStyle);
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     * @param date 日期字符串
     * @param parttern 日期格式
     * @return 日期
     */
    public static Date stringToDate(String date, String parttern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(parttern).parse(date);
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     * @param date 日期字符串
     * @param dateStyle 日期风格
     * @return 日期
     */
    public static Date stringToDate(String date, DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle == null) {
            List<Long> timestamps = new ArrayList<Long>();
            for (DateStyle style : DateStyle.values()) {
                Date dateTmp = stringToDate(date, style.getValue());
                if (dateTmp != null) {
                    timestamps.add(dateTmp.getTime());
                }
            }
            myDate = getAccurateDate(timestamps);
        } else {
            myDate = stringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     * @param date 日期
     * @param parttern 日期格式
     * @return 日期字符串
     */
    public static String dateToString(Date date, String parttern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(parttern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     * @param date 日期
     * @param dateStyle 日期风格
     * @return 日期字符串
     */
    public static String dateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = dateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     * @param date 旧日期字符串
     * @param dateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String stringToString(String date, DateStyle dateStyle) {
        return stringToString(date, null, dateStyle);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     * @param date 旧日期字符串
     * @param olddParttern 旧日期格式
     * @param newParttern 新日期格式
     * @return 新日期字符串
     */
    public static String stringToString(String date, String olddParttern, String newParttern) {
        String dateString = null;
        if (olddParttern == null) {
            DateStyle style = getDateStyle(date);
            if (style != null) {
                Date myDate = stringToDate(date, style.getValue());
                dateString = dateToString(myDate, newParttern);
            }
        } else {
            Date myDate = stringToDate(date, olddParttern);
            dateString = dateToString(myDate, newParttern);
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     * @param date 旧日期字符串
     * @param olddDteStyle 旧日期风格
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String stringToString(String date, DateStyle olddDteStyle, DateStyle newDateStyle) {
        String dateString = null;
        if (olddDteStyle == null) {
            DateStyle style = getDateStyle(date);
            dateString = stringToString(date, style.getValue(), newDateStyle.getValue());
        } else {
            dateString = stringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 获取日期。默认yyyy-MM-dd格式。失败返回null。
     * @param date 日期
     * @return 日期
     */
    public static String getDate(Date date) {
        return dateToString(date, DateStyle.YYYY_MM_DD);
    }


    public enum DateStyle {

        MM_DD("MM-dd"),
        YYYYMM("yyyyMM"),
        YYYYMMDD("yyyyMMdd"),
        YYYY_MM("yyyy-MM"),
        YYYY_MM_DD("yyyy-MM-dd"),
        MM_DD_HH_MM("MM-dd HH:mm"),
        MM_DD_HH_MM_SS("MM-dd HH:mm:ss"),
        YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),

        MM_DD_EN("MM/dd"),
        YYYY_MM_EN("yyyy/MM"),
        YYYY_MM_DD_EN("yyyy/MM/dd"),
        MM_DD_HH_MM_EN("MM/dd HH:mm"),
        MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"),
        YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),
        YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),

        /*适应民生银行接口返回的时间格式*/
        YYYY_MM_DD_HH_MM_SS_EN_MS("yyyyMMddHHmmss"),

        MM_DD_CN("MM月dd日"),
        YYYY_MM_CN("yyyy年MM月"),
        YYYY_MM_DD_CN("yyyy年MM月dd日"),
        MM_DD_HH_MM_CN("MM月dd日 HH:mm"),
        MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss"),
        YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm"),
        YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss"),

        HH_MM("HH:mm"),
        HH_MM_SS("HH:mm:ss");


        private String value;

        DateStyle(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


    /**
     * 获取上个月最后一天
     *
     * @return
     */
    public static DateTime getLastDayInLastMonth() {
        //获取前月的最后一天
        Calendar endCale = Calendar.getInstance();
        endCale.set(Calendar.DAY_OF_MONTH, 0);//上月最后一天
        return new DateTime(endCale);
    }

    /**
     * 获取上个月第一天
     *
     * @return
     */
    public static DateTime getFirstDayInLastMonth() {
        Calendar startCale = Calendar.getInstance();//获取当前日期
        startCale.add(Calendar.MONTH, -1);
        startCale.set(Calendar.DAY_OF_MONTH, 1);//设置第一天
        return new DateTime(startCale);
    }

    /**
     * 获取上个月最后一天
     *
     * @return
     */
    public static String getLastDayInLastMonth(String pattern) {
        //获取前月的最后一天
        return getLastDayInLastMonth().toString(pattern);
    }

    /**
     * 获取上个月第一天
     *
     * @return
     */
    public static String getFirstDayInLastMonth(String pattern) {
        return getFirstDayInLastMonth().toString(pattern);
    }

    /**
     * 获取某段时间内的所有日期
     *
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        DateFormat df = new SimpleDateFormat(datePattern);
        try {
            dBegin = df.parse(df.format(dBegin));
            dEnd = df.parse(df.format(dEnd));
        } catch (ParseException e) {
            logger.error("异常错误堆栈信息====》" + ExceptionUtils.getFullStackTrace(e));
        }
        List list = new ArrayList();
        list.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            list.add(calBegin.getTime());
        }
        return list;
    }

    /**
     * 获取当天的开始时间
     * @return
     */
    public static Date getTodayStartTime() {
        Calendar currentDate = new GregorianCalendar();

        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return currentDate.getTime();
    }

    /**
     * 获取 当天结束时间
     * @return
     */
    public static Date getTodayEndTime() {
        Calendar currentDate = new GregorianCalendar();

        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return currentDate.getTime();
    }

    public static Calendar getDateCalendar(String date) {
        //告警
        DateFormat df = new SimpleDateFormat(datePattern);
        Calendar calendar = Calendar.getInstance();
        Date d = null;
        try {
            d = df.parse(date);
            calendar.setTime(d);//设置当前时间
        } catch (ParseException e) {
            logger.error("异常错误堆栈信息====》" + ExceptionUtils.getFullStackTrace(e));
        }
        return calendar;
    }

    /**
     * 将日期对象格式化为日期、时间标准格式的字符串
     * @return
     */
    public static String currentGMT(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_GMT_PATTERN);
        return dtf.format(LocalDateTime.now(ZoneId.of("Asia/Shanghai")).minus(8, ChronoUnit.HOURS));
    }

    /**
     * 计算时间差
     * @param one
     * @param two
     * @return 分钟
     */
    public static long getDistanceTime(Date one, Date two) {
        long min,diff = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        min = ((diff / (60 * 1000)));

        return min;
    }

    /**
     * 获取指定日期的下一天
     * @param date 为空代表当天
     * @return
     */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if(Objects.nonNull(date)) {
            calendar.setTime(date);
        }
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();
    }

    /**
     * 获取指定日期的下一天
     * @param date 为空代表当天
     * @return
     */
    public static String getNextDayStr(Date date, String pattern) {
        return toString(getNextDay(date), pattern);
    }

    /**
     * 获取指定日期的上一天
     * @param date 为空代表当天
     * @return
     */
    public static Date getLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if(Objects.nonNull(date)) {
            calendar.setTime(date);
        }
        calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();
    }

    /**
     * 获取指定日期的下一天
     * @param date 为空代表当天
     * @return
     */
    public static String getLastDayStr(Date date, String pattern) {
        return toString(getLastDay(date), pattern);
    }

    /**
     * 字符串时间转换为时间戳
     * @param time
     * @return
     * @throws ParseException
     */
    public static Long dateToStamp(String time) throws ParseException {
        Date date = DATETIME_FORMAT.parse(time);
        long ts = date.getTime();
        return ts;
    }

    /**
     * 时间戳转换为时间字符串
     * @param time
     * @return
     */
    public static String stampToDate(Long time){
        String res ;
        long lt = new Long(time);
        Date date = new Date(lt);
        res = DATETIME_FORMAT.format(date);
        return res;
    }

    /**
     * 获取该日期的月份的最后一天
     * @param date 为空代表当天
     * @return
     */
    public static Date getLastDayInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if(Objects.nonNull(date)) {
            calendar.setTime(date);
        }
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取该日期的月份的第一天
     * @param date 为空代表当天
     * @return
     */
    public static Date getFirstDayInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if(Objects.nonNull(date)) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return calendar.getTime();
    }

    /**
     * 获取下一个小时区间的开始时间
     * @return
     */
    public static Date getNextHourStart(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY,1);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    /*
      *  获取过去几天的日期
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }


    /**
     * 获取当前小时，24小时制
     * @return
     */
    public static int getCurrHour(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int h = c.get(Calendar.HOUR_OF_DAY);
        return h;
    }

    /**
     * 获取当前日期的连续格式
     * @return yyyyMMdd
     */
    public static String getDateSerialStr(){
        return dateSerialFormate(TimeUtil.now());
    }

    public static long getDistanceMintues(Date endDate, Date nowDate){
        long nd = 1000 * 24 * 60 * 60;

        long nh = 1000 * 60 * 60;

        long nm = 1000 * 60;

        long diff = endDate.getTime() - nowDate.getTime();
        long min = diff % nd % nh / nm;
        long ns = 1000;
        long sec = diff % nd % nh % nm / ns;
        return sec;
    }
}
