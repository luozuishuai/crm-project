package com.shangma.cn.exception.handler;

import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.common.http.AxiosStatus;
import com.shangma.cn.exception.FileUploadException;
import com.shangma.cn.exception.JWTAuthorizationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理表单验证信息错误
     *
     * @param e 表单验证错误信息
     * @return 表单验证异常提示和异常信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AxiosResult<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        //获取表单异常信息
        BindingResult bindingResult = e.getBindingResult();
        HashMap<String, String> map = new HashMap<>();
        //如果有表单验证异常
        if (bindingResult.hasErrors()) {
            //获取所有异常信息
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            //遍历
            fieldErrors.forEach(fieldError -> {
                String field = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                //存入map中
                map.put(field, defaultMessage);
            });
        }
        //返回表单验证异常和异常信息
        return AxiosResult.error(AxiosStatus.VALID_ERROR, map);
    }

    /**
     * 处理图片上传异常
     */
    @ExceptionHandler(FileUploadException.class)
    public AxiosResult<Map<String,String>> fileUploadExceptionHandler(FileUploadException e){
        return AxiosResult.error(e.getAxiosStatus());
    }

    /**
     * 处理JWT验证异常
     */
    @ExceptionHandler(JWTAuthorizationException.class)
    public AxiosResult<Void> JWTAuthorizationExceptionHandler(JWTAuthorizationException e){
        return AxiosResult.error(e.getAxiosStatus());
    }
}
