package com.shangma.cn.exception;

import com.shangma.cn.common.http.AxiosStatus;


public class JWTAuthorizationException extends RuntimeException{

    private AxiosStatus axiosStatus;

    public JWTAuthorizationException(AxiosStatus axiosStatus) {
        this.axiosStatus = axiosStatus;
    }

    public AxiosStatus getAxiosStatus() {
        return axiosStatus;
    }

    public void setAxiosStatus(AxiosStatus axiosStatus) {
        this.axiosStatus = axiosStatus;
    }
}
