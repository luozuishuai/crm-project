package com.shangma.cn.entity;

import com.shangma.cn.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class Supplier extends BaseEntity {

    private String supplierName;

    private String supplierContact;

    private String supplierPhone;

    private String supplierEmail;

    private String supplierAddress;

    private String supplierBank;

    private String supplierBankCode;


}