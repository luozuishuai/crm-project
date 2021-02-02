package com.shangma.cn.entity;

import lombok.Data;

@Data
public class LoginAdmin {

    private String uuid;

    private Admin admin;

    private long loginTime;

    private long expireTime;
}
