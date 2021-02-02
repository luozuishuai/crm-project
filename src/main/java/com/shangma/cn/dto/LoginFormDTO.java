package com.shangma.cn.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginFormDTO {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @NotBlank(message = "请重新获取验证码")
    private String uuid;
}
