package com.qkl.common.bean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qkl.common.scanner.Scannable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public abstract class SimpleProperty<E> implements Scannable, Serializable {

    private static final Logger log = LoggerFactory.getLogger(SimpleProperty.class);

    protected static Map<String, List<SimpleProperty>> propertyListMap = Maps.newConcurrentMap();

    protected static Map<String, SimpleProperty> allProperty = Maps.newConcurrentMap();

    protected E code;

    protected String name;

    protected String group;

    public static <T extends SimpleProperty,E> T newInstance(Class<T> type, E code, String name) {
        T instance = null;
        try {
            instance = type.newInstance();
        } catch (Exception e) {
        }
        instance.code = code;
        instance.name = name;
        return instance;
    }

    public static <T extends SimpleProperty,E> T newInstance(Class<T> type,String group, E code, String name) {
        T instance = null;
        try {
            instance = type.newInstance();
        } catch (Exception e) {
        }
        instance.code = code;
        instance.name = name;
        instance.group = group;
        return instance;
    }

    public static List<SimpleProperty> getPropertyList(Class<? extends SimpleProperty> clazz) {
        List<SimpleProperty> list = propertyListMap.get(clazz.toString());
        if(list == null || list.size() == 0){
            initProperty(clazz);
            list = propertyListMap.get(clazz.toString());
        }
        return list;
    }

    public static Map<String, Object> getPropertyMap(Class<? extends SimpleProperty> clazz) {
        List<SimpleProperty> list = getPropertyList(clazz);
        Map<String, Object> map = new HashMap<>();
        for (SimpleProperty baseProperty : list) {
            map.put(baseProperty.getCode().toString(), baseProperty.getName());
        }
        return map;
    }

    public static <T extends SimpleProperty,E> T getProperty(Class<T> clazz, E code) {
        T instance = (T) allProperty.get(getMapKey(clazz, code));
        if(instance == null) {
            initProperty(clazz);
            instance = (T) allProperty.get(getMapKey(clazz, code));
        }
        return instance;
    }

    public static  <T extends SimpleProperty,E> Set<T> getPropertySet(Class<T> clazz, String[] codes){
        Set<T> set = new HashSet<>();
        if(codes != null && codes.length > 0){
            for(String code : codes){
                set.add(getProperty(clazz,code));
            }
        }
        return set;
    }


    /**
     * 在static方法还未执行前加载类，确保所有对象都初始化
     * @param clazz
     * @param <T>
     */
    private static <T extends SimpleProperty> void initProperty(Class<T> clazz){
        try {
            T t = clazz.newInstance();
        } catch (Exception e) {
        }
    }

    private static <E> String getMapKey(Class<? extends SimpleProperty> clazz, E code) {
        return clazz.getName() + ":" + code;
    }


    public E getCode() {
        return code;
    }

    public void setCode(E code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return (this == obj) || ((obj != null) && this.getClass().equals(obj.getClass())
                && this.getCode().equals(((SimpleProperty) obj).getCode()));
    }

    @Override
    public void scan() {
        List<SimpleProperty> pl = propertyListMap.get(this.getClass().toString());
        if (pl == null) {
            pl = Lists.newArrayList();
            propertyListMap.put(this.getClass().toString(), pl);
            log.info("init {} success", this.getClass());
        }
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (field.getType().equals(this.getClass())){
                SimpleProperty s = null;
                try {
                    s = (SimpleProperty)field.get(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    log.error("init SimplePorperty error:" + e);
                }
                pl.add(s);
                allProperty.put(s.getMapKey(s.getCode()), s);
            }
        }
    }

    protected String getMapKey() {
        return getMapKey(code);
    }

    protected String getMapKey(E code) {
        return getClass().getName() + ":" + code;
    }
}
