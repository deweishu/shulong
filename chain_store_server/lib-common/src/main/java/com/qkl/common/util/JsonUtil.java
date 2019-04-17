/**
 *
 */
package com.qkl.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonUtil {
    public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper = createMapper();

    public static ObjectMapper getMapper() {
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

        result.setDateFormat(DATETIME_FORMAT);

        // 设置输出时包含属性的风格
        result.setSerializationInclusion(Include.ALWAYS);

        return result;
    }

    public static Map jsonToMap(String json) {
        try {
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            logger.warn("read json string error", e);
            return null;
        }
    }

    /**
     * 获取json中的一个值
     *
     * @param json
     * @param key
     * @return
     */
    public static String getValue(String json, String key) {
        Map map = jsonToMap(json);
        if (map == null) {
            return null;
        }
        Object obj = map.get(key);
        if (obj == null) {
            return null;
        }
        return obj.toString();
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
            String s = mapper.writeValueAsString(object);
            if ("null".equals(s))
                s = null;
            return s;
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    public static <E> E jsonToBean(InputStream input, Class<E> clazz) {
        if (input == null) {
            return null;
        }
        try {
            return mapper.readValue(input, clazz);
        } catch (IOException e) {
            logger.warn("parse json error", e);
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

    public static <E> E jsonToBean(String str, TypeReference<E> typeReference) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            return mapper.readValue(str, typeReference);
        } catch (IOException e) {
            logger.warn("parse json string error:" + str, e);
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

    public static JsonNode parseNode(String text) {
        try {
            return mapper.readTree(text);
        } catch (IOException e) {
            logger.warn("parse json error:" + text, e);
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

    /**
     * 输出JSONP格式数据.
     */
    public static String beanToJsonP(String functionName, Object object) {
        return beanToJson(new JSONPObject(functionName, object));
    }
}
