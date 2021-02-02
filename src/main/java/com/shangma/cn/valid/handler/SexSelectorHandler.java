package com.shangma.cn.valid.handler;

import com.shangma.cn.valid.annotation.SexSelector;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class SexSelectorHandler implements ConstraintValidator<SexSelector,String> {

    private List<String> sexList;

    @Override
    public void initialize(SexSelector constraintAnnotation) {
        String[] strings = constraintAnnotation.sexList();
        sexList = Arrays.asList(strings);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return sexList.contains(value);
    }
}
