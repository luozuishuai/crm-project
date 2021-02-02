package com.shangma.cn.exception;

import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.common.http.AxiosStatus;

public class FileUploadException extends RuntimeException{

    private AxiosStatus axiosStatus;

    public AxiosStatus getAxiosStatus() {
        return axiosStatus;
    }

    public void setAxiosStatus(AxiosStatus axiosStatus) {
        this.axiosStatus = axiosStatus;
    }

    public FileUploadException(AxiosStatus axiosStatus) {

        this.axiosStatus = axiosStatus;
    }
}
