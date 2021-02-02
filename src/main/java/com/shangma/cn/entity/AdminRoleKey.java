package com.shangma.cn.entity;

import com.shangma.cn.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRoleKey extends BaseEntity {

    private Long adminId;

    private Long roleId;
}