package com.shangma.cn.common.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
//转json值的时候只转不为null的
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AxiosResult<T> {

    private int status;
    private String message;
    private T data;

    private AxiosResult() {
    }

    /**
     * 封装数据公共方法
     *
     * @param axiosStatus
     * @param data
     * @param <T>
     * @return
     */
    private static <T> AxiosResult<T> setData(AxiosStatus axiosStatus, T data) {
        AxiosResult<T> axiosResult = new AxiosResult<>();
        axiosResult.setData(data);
        axiosResult.setStatus(axiosStatus.getStatus());
        axiosResult.setMessage(axiosStatus.getMessage());
        return axiosResult;
    }

    /**
     * 返回成功，不带数据
     */
    public static <T> AxiosResult<T> success() {
        return setData(AxiosStatus.OK, null);
    }

    /**
     * 返回成功，带数据
     */
    public static <T> AxiosResult<T> success(T data) {
        return setData(AxiosStatus.OK, data);
    }

    /**
     * 返回成功，带自定义状态码
     */
    public static <T> AxiosResult<T> success(AxiosStatus axiosStatus) {
        return setData(axiosStatus, null);
    }


    /**
     * 返回成功，带自定义状态码带数据
     */
    public static <T> AxiosResult<T> success(AxiosStatus axiosStatus, T data) {
        return setData(axiosStatus, data);
    }

    /**
     * 返回错误,不带数据
     */
    public static <T> AxiosResult<T> error() {
        return setData(AxiosStatus.ERROR, null);
    }

    /**
     * 返回错误,带数据
     */
    public static <T> AxiosResult<T> error(T data) {
        return setData(AxiosStatus.ERROR, data);
    }

    /**
     * 返回错误,带自定义状态码
     */
    public static <T> AxiosResult<T> error(AxiosStatus axiosStatus) {
        return setData(axiosStatus, null);
    }


    /**
     * 返回错误,带自定义状态码带数据
     */
    public static <T> AxiosResult<T> error(AxiosStatus axiosStatus, T data) {
        return setData(axiosStatus, data);
    }
}
