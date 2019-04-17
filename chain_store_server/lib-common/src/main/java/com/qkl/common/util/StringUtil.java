package com.qkl.common.util;

import com.google.common.io.BaseEncoding;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 * Created by dengjihai on 2018/3/28.
 */
public class StringUtil {

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w\\.\\-]+@[\\w\\.\\-]+(\\.[\\w\\-]{2,3}){1,2}$");

    public static final Pattern MOBILE_PATTERN = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0,6-8])|(18[0-9]))\\d{8}$");

    public static final char[] charMap;
    static {
        charMap = new char[64];
        for (int i = 0; i < 10; i++) {
            charMap[i] = (char) ('0' + i);
        }
        for (int i = 10; i < 36; i++) {
            charMap[i] = (char) ('a' + i - 10);
        }
        for (int i = 36; i < 62; i++) {
            charMap[i] = (char) ('A' + i - 36);
        }
        charMap[62] = '_';
        charMap[63] = '-';
    }

    /**
     * 判空
     */
    public static boolean isNil(String string) {
        return string == null || string.length() == 0;
    }

    /**

     * 是否是英文

     * @param c

     * @return

     */

    public static boolean isEnglish(String charaString){

        return charaString.matches("^[a-zA-Z]*");

    }

    public static boolean isChinese(String str){

        String regEx = "[\\u4e00-\\u9fa5]+";

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(str);

        if(m.find())

            return true;

        else

            return false;

    }


    /**
     * 判非空
     */
    public static boolean isNotNil(String string) {
        return !isNil(string);
    }
    /**
     * 判空白字符串
     */
    public static boolean isBlank(String str) {
        return isNil(str) || 0 == str.trim().length();
    }

    /**
     * 判空白字符串
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isMobile(String input) {
        return MOBILE_PATTERN.matcher(input).matches();
    }

    public static String base64(String string) {
        return base64(string.getBytes());
    }

    public static String base64(byte[] bs) {
        return BaseEncoding.base64().encode(bs);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取更短的UUID，长度为22
     */
    public static String get64UUID() {
        String uuid = "0" + getUUID();
        return hexTo64(uuid);
    }

    private static String hexTo64(String hex) {
        StringBuffer r = new StringBuffer();
        int index = 0;
        int[] buff = new int[3];
        int l = hex.length();
        for (int i = 0; i < l; i++) {
            index = i % 3;
            buff[index] = Integer.parseInt("" + hex.charAt(i), 16);
            if (index == 2) {
                r.append(charMap[buff[0] << 2 | buff[1] >>> 2]);
                r.append(charMap[(buff[1] & 3) << 4 | buff[2]]);
            }
        }
        return r.toString();
    }


    /**
     * 字符串转字符数组
     *
     * @param str
     * @param radix 多少进制
     * @return
     */
    public static byte[] toBytes(String str, int radix) {
        byte[] srcBytes = str.getBytes();

        // 2个16进制字符占用1个字节，8个二进制字符占用1个字节
        int size = 2;
        if (radix == 2) {
            size = 8;
        }

        byte[] tgtBytes = new byte[srcBytes.length / size];
        for (int i = 0; i < srcBytes.length; i = i + size) {
            String tmp = new String(srcBytes, i, size);
            tgtBytes[i / size] = (byte) Integer.parseInt(tmp, radix);
        }
        return tgtBytes;
    }

    /**
     * string数组转换int数组
     */
    public static int[] toIntegers(String... strs){
        int[] intArr = new int[strs.length];
        for(int i =0;i<strs.length;i++){
            intArr[i] = Integer.parseInt(strs[i]);
        }
        return intArr;
    }

    /**
     * IpUtils工具类方法
     * 获取真实的ip地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
//        String ip = request.getHeader("X-Forwarded-For");
//        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
//            //多次反向代理后会有多个ip值，第一个ip才是真实ip
//            int index = ip.indexOf(",");
//            if(index != -1){
//                return ip.substring(0,index);
//            }else{
//                return ip;
//            }
//        }
//        ip = request.getHeader("X-Real-IP");
//        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
//            return ip;
//        }
//        return request.getRemoteAddr();
        String ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }



    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    /**
     * 将手机号中间四位变成 * 号
     * @param phone
     * @return
     */
    public static String mobilePhoneReplace(String phone) {
        if (isNil(phone)) {
            return "NaN";
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

}
