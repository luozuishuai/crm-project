package com.shangma.cn.valid.annotation;

import com.shangma.cn.valid.handler.SexSelectorHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import java.lang.annotation.*;
import java.util.List;

@Constraint(validatedBy = {SexSelectorHandler.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SexSelector {
    String message() default "{javax.validation.constraints.Email.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 初始化性别可选列表
     * @return
     */
    String[] sexList() default {};
}
