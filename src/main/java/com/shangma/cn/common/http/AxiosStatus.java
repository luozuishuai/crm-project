package com.shangma.cn.common.http;

public enum AxiosStatus {
    OK(20000,"操作成功"),
    ERROR(50000,"操作失败"),
    VALID_ERROR(25000,"表单验证异常"),
    FILEUPLOAD_FILE_MAX(24001,"上传的文件过大"),
    FILEUPLOAD_NOT_IMAGE(24002,"上传的文件不是一个图片"),
    FILEUPLOAD_EXT_ERROR(24003,"上传的文件格式错误"),
    CAPTCHA_ERROR(26000,"验证码错误"),
    ADMINACCOUNT_ERROR(27000,"用户名错误"),
    PASSWORD_ERROR(28000,"密码错误"),
    JWT_ERROR(29999,"token解析异常"),
    ;

    AxiosStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
