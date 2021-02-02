package com.shangma.cn.common.untils;

import com.shangma.cn.common.cache.RedisCacheUtils;
import com.shangma.cn.common.constant.RedisKeyConstant;

public class CaptchaCacheUtils {

    /**
     * 设置验证码缓存
     */
    public static void setCaptcha(String uuid,String code){
        RedisCacheUtils.setRedisCacheWithDefaultExpireTime(RedisKeyConstant.CAPTCHA_CODE+uuid,code);
    }

    /**
     * 取出验证码
     */
    public static String getCaptcha(String uuid){
        return RedisCacheUtils.getRedisCache(RedisKeyConstant.CAPTCHA_CODE+uuid);
    }
}
