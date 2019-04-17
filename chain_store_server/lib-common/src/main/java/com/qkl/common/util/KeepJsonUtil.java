package com.qkl.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by niejiuqian on 2017/11/3.
 * 保留所有字段json帮助类
 */
public class KeepJsonUtil {
    private static Logger logger = LoggerFactory.getLogger(KeepJsonUtil.class);

    private static ObjectMapper mapper = createMapper();

    public static ObjectMapper getMapper() {
        if (null == mapper) mapper = createMapper();
        return mapper;
    }
    private static ObjectMapper createMapper() {
        ObjectMapper result = new ObjectMapper();
        // 设置jdk8时间支持
        result.registerModule(new ParameterNamesModule());
        result.registerModule(new Jdk8Module());
        result.registerModule(new JavaTimeModule());
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        result.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        result.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        result.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return result;
    }

    /**
     * @param object
     * @param <E>
     * @return
     */
    public static <E> String beanToJson(E object) {
        try {
            if (Objects.isNull(object)) {
                return null;
            }
            String s = getMapper().writeValueAsString(object);
            if ("null".equals(s))
                s = null;
            return s;
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> List<E> jsonToList(String str, Class<?> elementClasses) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            JavaType javaType = getCollectionType(ArrayList.class, elementClasses);
            return (List<E>) mapper.readValue(str, javaType);
        } catch (IOException e) {
            logger.warn("parse json string error:" + str, e);
            return null;
        }
    }

    public static <E> E jsonToBean(String str, Class<E> clazz) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            return mapper.readValue(str, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + str, e);
            return null;
        }
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
