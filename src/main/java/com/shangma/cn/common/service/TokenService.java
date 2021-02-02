package com.shangma.cn.common.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.shangma.cn.common.cache.RedisCacheUtils;
import com.shangma.cn.common.constant.RedisKeyConstant;
import com.shangma.cn.common.http.AxiosStatus;
import com.shangma.cn.common.untils.JSONUtils;
import com.shangma.cn.common.untils.ServletUtils;
import com.shangma.cn.entity.Admin;
import com.shangma.cn.entity.LoginAdmin;
import com.shangma.cn.exception.JWTAuthorizationException;
import com.sun.xml.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@Component
public class TokenService {

    //密钥
    private static final String SECRET = "SHUAI";

    //默认过期时间（7天）
    private static final long EXPIRETIME = 1000 * 60 * 60 * 24 * 7;

    /**
     * 创建token key为uuid value为uuid的值
     */
    public String creatToken(String uuid) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            String token = JWT.create()
                    .withIssuer("luozuishuai")    //jwt签发者
                    .withClaim("uuid", uuid)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            log.error("创建token错误：" + exception);
        }
        return "";
    }

    /**
     * 获取token字符串
     */
    public String getTokenStr(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {
            String token = authorization.split(" ")[1];
            return token;
        }
        return "";

    }

    /**
     * 解析token
     */
    public DecodedJWT verifyToken(String tokenStr) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("luozuishuai")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(tokenStr);
            return jwt;
        } catch (Exception exception) {
            //Invalid signature/claims
            log.error("token解析异常：" + exception);
            throw new JWTAuthorizationException(AxiosStatus.JWT_ERROR);
        }
    }

    /**
     * 从token中获取登录用户Admin信息
     */
    public Admin getAdminFromToken(HttpServletRequest request) {
        String tokenStr = getTokenStr(request);
        DecodedJWT decodedJWT = verifyToken(tokenStr);
        if (decodedJWT != null) {
            String adminStr = decodedJWT.getClaim("admin").asString();
            Admin admin = JSONUtils.str2Obj(adminStr, Admin.class);
            return admin;
        }
        return null;
    }

    /**
     * 获取token中的uuid
     */
    public String getTokenUUID(HttpServletRequest request) {
        String tokenStr = getTokenStr(request);
        DecodedJWT decodedJWT = verifyToken(tokenStr);
        // 未登录时 token为null会空指针异常
        if (decodedJWT != null) {
            Claim uuid = decodedJWT.getClaim("uuid");
            if(uuid != null){
                return uuid.asString();
            }
        }
        return "";
    }


    /**
     * 从Redis中获取登录用户Admin信息
     */
    public LoginAdmin getLoginAdminFromRedis(HttpServletRequest request) {
        String uuid = getTokenUUID(request);
        String loginAdmin = RedisCacheUtils.getRedisCache(RedisKeyConstant.LOGIN_ADMIN + uuid);
        //#TODO 未登录时会空指针异常
        return JSONUtils.str2Obj(loginAdmin, LoginAdmin.class);
    }


    public String creatTokenAndCacheLoginAdmin(Admin admin) {
        String uuid = UUID.randomUUID().toString();
        LoginAdmin loginAdmin = new LoginAdmin();
        loginAdmin.setUuid(uuid);
        loginAdmin.setAdmin(admin);
        refreshLoginTime(loginAdmin);

        //缓存LoginAdmin到Redis
        cacheLoginAdmin(loginAdmin);
        return creatToken(uuid);
    }

    /**
     * 刷新过期时间
     */
    public void refreshLoginTime(LoginAdmin loginAdmin) {
        loginAdmin.setLoginTime(System.currentTimeMillis());
        loginAdmin.setExpireTime(System.currentTimeMillis() + EXPIRETIME);
    }

    public void cacheLoginAdmin(LoginAdmin loginAdmin) {
        RedisCacheUtils.setRedisCacheWithCustomizeMinutes(RedisKeyConstant.LOGIN_ADMIN + loginAdmin.getUuid(), JSONUtils.obj2Str(loginAdmin), 1 * 60 * 24 * 10L);
    }

    /**
     * 获取登录用户id
     */
    public Long getAdminId(HttpServletRequest request){
        LoginAdmin loginAdmin = getLoginAdminFromRedis(request);
        return loginAdmin.getAdmin().getId();
    }

    /**
     * 验证token是否正确
     */
    public boolean verifyAndAuthorzationToken(HttpServletRequest request){
        String tokenStr = getTokenStr(request);
        DecodedJWT decodedJWT = verifyToken(tokenStr);
        if(decodedJWT != null){
            //刷新用户时间和过期时间
            refreshLoginAdmin(request);
            return true;
        }else {
            throw new JWTAuthorizationException(AxiosStatus.JWT_ERROR);
        }

    }

    /**
     * 刷新用户登录时间
     */
    public void refreshLoginAdmin(HttpServletRequest request){
        LoginAdmin loginAdmin = getLoginAdminFromRedis(request);
        //现在时间
        long now = System.currentTimeMillis();
        //用户最后操作时间
        long loginTime = loginAdmin.getLoginTime();
        //登录过期时间
        long expireTime = loginAdmin.getExpireTime();
        //如果现在的时间距离上次请求的时间大于5分钟 那么登录失效
        if((now-loginTime) / 1000 / 60 > 5){
            //操作超时，请重新登录
            throw new JWTAuthorizationException(AxiosStatus.JWT_ERROR);
        }
        //如果现在的时间大于过期时间，那么登录失效
        if(now > expireTime){
            //登录过期，请重新登录
            throw new JWTAuthorizationException(AxiosStatus.JWT_ERROR);
        }
        //如果现在的时间接近过期时间 那么刷新过期时间
        if((expireTime - loginTime) / 1000 < 24){
            loginAdmin.setExpireTime(now + EXPIRETIME);
        }
        loginAdmin.setLoginTime(now);
        cacheLoginAdmin(loginAdmin);

    }

}
