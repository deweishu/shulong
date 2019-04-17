package com.qkl.common.util;

import java.math.BigDecimal;

/**
 * 数值工具类，不支持float和double转为bigdecimal，以免出现错误
 * Created by dengjihai on 2018/3/28.
 */
public class BigDecimalUtil {

    public static BigDecimal DEFAULT_ZERO = new BigDecimal("0");

    public static BigDecimal bigDecimal(String data) {
        if(StringUtil.isNil(data)) {
            return DEFAULT_ZERO;
        }
        return new BigDecimal(data);
    }

    public static BigDecimal bigDecimal(int data) {
        return new BigDecimal(data+"");
    }

    public static BigDecimal bigDecimal(double data) {
        return new BigDecimal(data+"");
    }


    /**
     * 截取小数
     * @param scale 小数点位数
     * @param bigDecimal
     * @return
     */
    public static BigDecimal cutBigDecimal(int scale , BigDecimal bigDecimal) {
        if (null == bigDecimal) return BigDecimal.ZERO;
        return  bigDecimal.setScale(scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 按指定位数四舍五入
     * @param scale 小数点位数
     * @param bigDecimal
     * @return
     */
    public static BigDecimal roundHalfUp(int scale , BigDecimal bigDecimal) {
        if (null == bigDecimal) return BigDecimal.ZERO;
        return  bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 多个数相加
     * @param args 变参
     * @return
     */
    public static BigDecimal add(BigDecimal... args) {
        BigDecimal total = DEFAULT_ZERO;
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                continue;
            }
            total = total.add(args[i]);
        }
        return total;
    }

    /**
     * 多个数相加，保留指定位数精度
     * @param scale 小数点位数
     * @param roundParam 四舍五入还是直接截取 0 :四舍五入，1：直接截取
     * @param args 变参
     * @return
     */
    public static BigDecimal add(int scale, int roundParam, BigDecimal... args) {
        BigDecimal total = DEFAULT_ZERO;
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                continue;
            }
            total = total.add(args[i]);
        }
        return total.setScale(scale, roundParam);
    }

    /**
     * 多个数相乘，并保留多少位小数
     * @param scale 多少位小数
     * @param roundParam 四舍五入还是直接截取 0 :四舍五入，1：直接截取
     * @param args 参数列表
     * @return
     */
    public static BigDecimal multiply(int scale, int roundParam, BigDecimal... args) {
        if (null != args && args.length > 0) {
            BigDecimal total = args[0];
            if (args.length > 1) {
                for (int i = 1; i < args.length; i++) {
                    if (args[i] == null) {
                        continue;
                    }
                    total = total.multiply(args[i]);
                }
            }
            return total.setScale(scale, roundParam);
        }
        return DEFAULT_ZERO;
    }

    /**
     * 多个数相乘
     * @param args 参数列表
     * @return
     */
    public static BigDecimal multiply(BigDecimal... args) {
        if (null != args && args.length > 0) {
            BigDecimal total = args[0];
            if (args.length > 1) {
                for (int i = 1; i < args.length; i++) {
                    if (args[i] == null) {
                        continue;
                    }
                    total = total.multiply(args[i]);
                }
            }
            return total;
        }
        return DEFAULT_ZERO;
    }

    /**
     * 除以多个数，并保留固定位数小数，
     * @param scale 多少位小数
     * @param roundParam 舍五入还是直接截取 0 :四舍五入，1：直接截取
     * @param dividend 被除数
     * @param divisors 除数
     * @return
     */
    public static BigDecimal divide(int scale, int roundParam, BigDecimal dividend, BigDecimal... divisors) {
        return getDivideBigDecimal(dividend, scale, roundParam , divisors);
    }

    /**
     * 除以多个数, 默认保留2位，做四舍五入
     * @param dividend 被除数
     * @param divisors 除数
     * @return
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal... divisors) {
        return getDivideBigDecimal(dividend, 2, BigDecimal.ROUND_HALF_UP, divisors);
    }

    private static BigDecimal getDivideBigDecimal(BigDecimal dividend, int scale, int roundingMode, BigDecimal[] divisors) {
        if(null != dividend && dividend.compareTo(DEFAULT_ZERO) != 0) {
            if (null != divisors && divisors.length > 0) {
                for (int i = 0; i < divisors.length; i++) {
                    if (divisors[i] == null) {
                        continue;
                    }
                    if(divisors[i].compareTo(DEFAULT_ZERO) == 0) {
                        return DEFAULT_ZERO;
                    }
                    dividend = dividend.divide(divisors[i], scale, roundingMode);
                }
            }
            return dividend;
        }
        return DEFAULT_ZERO;
    }


    /**
     * 连续相减 向下截取
     * @param scale 保留小数点位数
     * @param roundParam 四舍五入还是直接截取 0 :四舍五入，1：直接截取
     * @param minuend 被减数
     * @param args 多个减数
     * @return
     */
    public static BigDecimal sub(int scale, int roundParam, BigDecimal minuend, BigDecimal... args) {
        if(minuend != null) {
            for (int i = 0; i < args.length; i++) {
                if(args[i] != null) {
                    minuend = minuend.subtract(args[i]);
                }
            }
            return minuend.setScale(scale, roundParam);
        }
        return DEFAULT_ZERO;
    }

    /**
     * 连续相减
     * @param minuend 被减数
     * @param args 多个减数
     * @return
     */
    public static BigDecimal sub(BigDecimal minuend, BigDecimal... args) {
        if(minuend != null) {
            for (int i = 0; i < args.length; i++) {
                if(args[i] != null) {
                    minuend = minuend.subtract(args[i]);
                }
            }
            return minuend;
        }
        return DEFAULT_ZERO;
    }

    /**
     * 取相反数
     * @param bigDecimal 数据
     * @return
     */
    public static BigDecimal opposite(BigDecimal bigDecimal) {
        return bigDecimal.multiply(new BigDecimal("-1"));
    }

    /**
     * 除法
     * @param a 除数
     * @param b 被除数
     * @return
     */
    public static BigDecimal divide(BigDecimal a,BigDecimal b){
        return b.divide(a);
    }

    /**
     *  BigDecimal转字符串 四舍五入
     */
    public static String decimalToString(BigDecimal decimal,int roundingMode){
        if(decimal==null) decimal= BigDecimal.ZERO;
        return decimal.setScale(BigDecimal.ROUND_HALF_UP,roundingMode).toString();
    }
    /**
     *  字符串转BigDecimal 四舍五入
     */
    public static BigDecimal stringToDecimal(String value,int roundingMode){
        BigDecimal decimal = BigDecimal.ZERO;
        if(StringUtil.isNotBlank(value)){
            decimal = new BigDecimal(value);
        }
        return decimal.setScale(BigDecimal.ROUND_HALF_UP,roundingMode);
    }
}
