package com.shangma.cn.common.untils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class JSONUtils {

    static ObjectMapper objectMapper = new JsonMapper();

    /**
     * 对象转字符串
     */
    public static String obj2Str(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("对象转字符串异常：" + e);
        }
        return "";
    }

    /**
     * JSON转对象
     */
    public static <T> T str2Obj(String json, Class<T> clazz) {
        T t = null;
        try {
            t = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 字符串转list集合
     */
    public static <T> List<T> str2List(String str, Class<T> clazz) {
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        List<T> list = null;
        try {
            list = objectMapper.readValue(str, collectionType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 字符串转map集合
     */
    public static <K, V> Map<K, V> str2List(String str, Class<K> kClazz, Class<V> vClazz) {
        MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, kClazz, vClazz);
        Map<K,V> map = null;
        try {
            map = objectMapper.readValue(str,mapType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }
}
