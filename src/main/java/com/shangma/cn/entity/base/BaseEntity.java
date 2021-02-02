package com.shangma.cn.entity.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.shangma.cn.common.container.SpringBeanUtils;
import com.shangma.cn.common.service.TokenService;
import com.shangma.cn.common.untils.ServletUtils;
import com.shangma.cn.entity.Admin;
import com.shangma.cn.valid.group.AddGroup;
import com.shangma.cn.valid.group.UpdateGroup;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Data
public class BaseEntity {

    @Null(message = "添加时id必须为空",groups = AddGroup.class)
    @NotNull(message = "编辑时id必须不为空",groups = UpdateGroup.class)
    @ExcelProperty(value = "员工id",index = 0)
    private Long id;

    @ExcelIgnore
    private Date addTime;

    @ExcelIgnore
    private Long creatorId;

    @ExcelIgnore
    private Date updateTime;

    @ExcelIgnore
    private Long updateId;

    public void doSetDate() {
        //从容器中获取TokenService
        TokenService tokenService = SpringBeanUtils.getBean(TokenService.class);
        //从tokenService中获取AdminId
        Long adminId = tokenService.getAdminId(ServletUtils.getRequest());
        if (isNew()) {
            setAddTime(new Date());
            setCreatorId(adminId);
        }
        setUpdateTime(new Date());
        setUpdateId(adminId);

    }

    private boolean isNew() {
        if (addTime == null && creatorId == null) {
            return true;
        } else {
            return false;
        }
    }

}
