package com.shangma.cn.common.cache;

import com.shangma.cn.common.container.SpringBeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisCacheUtils {

    //默认是3分钟有效
    private static final long expireTime = 3;

    /**
     * 拿到StringRedisTemplateBean
     */
    public static StringRedisTemplate getStringRedisTemplate() {
        return SpringBeanUtils.getBean(StringRedisTemplate.class);
    }

    /**
     * 永久设置
     */
    public static void setRedisCache(String key, String value) {
        getStringRedisTemplate().opsForValue().set(key, value);
    }

    /**
     * 默认时间设置
     */
    public static void setRedisCacheWithDefaultExpireTime(String key, String value) {
        getStringRedisTemplate().opsForValue().set(key, value, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 自定义时间设置
     */
    public static void setRedisCacheWithCustomizeMinutes(String key, String value, Long expireTime) {
        getStringRedisTemplate().opsForValue().set(key, value, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 取值
     */
    public static String getRedisCache(String key) {
        return getStringRedisTemplate().opsForValue().get(key);
    }

    /**
     * 删除值
     */
    public static void deleteRedisCache(String key) {
        getStringRedisTemplate().delete(key);
    }
}
