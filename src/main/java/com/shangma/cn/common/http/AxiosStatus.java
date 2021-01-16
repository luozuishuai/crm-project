package com.shangma.cn.common.http;

public enum AxiosStatus {
    OK(20000,"操作成功"),
    ERROR(50000,"操作失败"),
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
