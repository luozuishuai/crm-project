package com.shangma.cn.interceptor;

import com.shangma.cn.common.service.TokenService;
import com.shangma.cn.entity.Admin;
import com.shangma.cn.entity.LoginAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        boolean b = tokenService.verifyAndAuthorzationToken(request);

        return b;


//        if ("OPTIONS".equals(request.getMethod().toString())) {
//
//            try {
//                return HandlerInterceptor.super.preHandle(request, response, handler);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        LoginAdmin loginUser = tokenService.getLoginAdminFromRedis();
//        if (loginUser == null) {
//            System.out.println("未登录");
//        } else {
//            System.out.println("已登录");
//        }
//        return true;

//
//        try {
//            response.sendRedirect("http://localhost:8080/login");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
