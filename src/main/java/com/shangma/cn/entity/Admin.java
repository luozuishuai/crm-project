package com.shangma.cn.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.shangma.cn.entity.base.BaseEntity;
import com.shangma.cn.valid.annotation.SexSelector;
import com.shangma.cn.valid.group.AddGroup;
import com.shangma.cn.valid.group.UpdateGroup;
import lombok.Data;
import lombok.ToString;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
@ColumnWidth(23)    //列宽
@HeadRowHeight(40)  //表头行高度
@ContentRowHeight(70) //内容行的高度
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND,fillForegroundColor = 6)
@HeadFontStyle(fontHeightInPoints = 20)    //表头字体样式
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentFontStyle(fontHeightInPoints = 20)
public class Admin extends BaseEntity{

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{3,8}$",message = "用户名格式错误",groups = {AddGroup.class, UpdateGroup.class})
    @NotBlank(message = "用户名不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty("员工账号")
    private String adminAccount;

//    @NotNull    判断是否为null
//    @NotEmpty     判断长度是否为0
    @NotBlank(message = "昵称不能为空",groups = {AddGroup.class, UpdateGroup.class})     //判断是否是空串和纯空白字符
    @Size(min = 3,max = 8,message = "昵称必须在4-8位",groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty("员工昵称")
    private String nickName;

    @Email(groups = {AddGroup.class, UpdateGroup.class})
    @NotBlank(message = "邮箱不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @ColumnWidth(40)
    @ExcelProperty("员工邮箱")
    private String adminEmail;

    @NotBlank(message = "手机号不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-3,5-9]|16[2567]|17[0-8]|18[0-9]|19[0-3,5-9])\\d{8}$",message = "手机号格式错误",groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty("手机号码")
    @ColumnWidth(30)
    private String adminPhone;

    @SexSelector(sexList = {"0","1","2"},message = "性别选择不合法",groups = {AddGroup.class, UpdateGroup.class})
    @ExcelIgnore
    private String adminSex;

    @URL(message = "头像必须是一个正确的url地址",groups = {AddGroup.class, UpdateGroup.class})
    @ExcelIgnore
    private String adminAvatar;

    @ExcelIgnore
    private Boolean adminStatus;

    @ExcelProperty("员工名称")
    private String adminName;

    @ExcelIgnore
    private String adminPassword;

    @NotBlank(message = "身份证号不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty("身份证号")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$",message = "身份证号码格式错误",groups = {AddGroup.class, UpdateGroup.class})
    @ColumnWidth(40)
    private String adminCode;

    @NotBlank(message = "地址不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty("员工地址")
    @ColumnWidth(40)
    private String adminAddress;

    @ExcelProperty("薪资")
    private double adminSalary;


    @NotBlank(message = "员工部门不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty("所在部门")
    private String adminDept;

    @ExcelIgnore
    private Boolean delFlag;

    @ExcelProperty("员工头像")
    @ColumnWidth(18)
    private java.net.URL url;

    @ExcelIgnore
    private List<Long> RoleIds;

}