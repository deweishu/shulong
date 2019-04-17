package com.qkl.common.util;


import org.springframework.util.Assert;

import java.util.*;

/**
 * 集合类相关工具方法
 * Created by dengjihai on 2018/3/28.
 */
@SuppressWarnings("unchecked")
public class CollectionUtil {

    /**
     * 空集合检查。如果集合为null或者集合没有元素返回true。
     */
    public static boolean isNil(Collection<? extends Object> collection){
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotNil(Collection<? extends Object> collection){
        return !isNil(collection);
    }

    public  static  <T extends Object>  boolean isNil(T... objs){
        if (objs == null) return true;
        return isNil(Arrays.asList(objs));
    }

    public  static  <T extends Object>  boolean isNotNil(T... objs){
        if (objs == null) return false;
        return !isNil(Arrays.asList(objs));
    }


    /**
     * 计算list可按指定元素个数分成的组数目
     *
     * @param list
     * @param gSize
     * @return
     */
    public static int getGroupNum(List<? extends Object> list, int gSize) {
        Assert.isTrue(gSize > 0 , "groupSize 必须大于 0");

        if (gSize == 1) {
            return list.size();
        }

        if (list.size() % gSize == 0) {
            return list.size() / gSize;
        }

        return 1 + (list.size() / gSize);
    }

    /**
     * 活动指定组的List
     *
     * @param list
     * @param gSize
     * @param gIndex
     * @return
     */
    public static <E> List<E> getGroupList(List<E> list, int gSize, int gIndex) {
        Assert.isTrue(gSize > 0 , "must groupSize > 0");
        Assert.isTrue(gIndex >= 0 , "must groupIndex >= 0 ");

        int startIndex = gIndex * gSize;
        int endIndex = ((gIndex + 1) * gSize) - 1;

        Assert.isTrue(startIndex < list.size() , "must startIndex < list.size()");

        if (endIndex >= list.size()) {
            endIndex = list.size() - 1;
        }

        return new ArrayList<E>(list.subList(startIndex, endIndex + 1));
    }

    public static <E> List<List<E>> groupList(List<E> list, int gSize) {
        List<List<E>> groups = new ArrayList<List<E>>();
        if (list == null) {
            return groups;
        }
        for (int i = 0; i < getGroupNum(list, gSize); i++) {
            groups.add(getGroupList(list, gSize, i));
        }
        return groups;
    }

    public static <T> Set<T> createSetOf(T... objects){
        Set<T> set = new HashSet<T>();

        for (T obj : objects) {
            set.add(obj);
        }

        return set;
    }

    public static <T> Set<T> createSetOf(Collection<T> objects) {
        Set<T> set = new HashSet<T>();

        for (T obj : objects) {
            set.add(obj);
        }

        return set;
    }

    public  static  <T> String toElementString(Collection<T> c){
        String string = c.toString();
        return c.isEmpty() ? "" : string.substring(1,string.length()-1);
    }

    public static String splitJointElement(
            Collection<? extends Object> collection, String splitJointSign) {
        if(isNil(collection)){
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        for (Object obj : collection) {
            buffer.append(obj);
            buffer.append(splitJointSign);
        }
        String str = buffer.toString();
        str = str.substring(0, str.length() - splitJointSign.length());
        return str;
    }

    public static <E> E last(Collection<E> elements) {
        Assert.isTrue( elements.size() > 0 , "集合中元素个数不能为空");
        E last = null;
        for (E element : elements) {
            last = element;
        }
        return last;
    }
}
