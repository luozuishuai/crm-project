package com.shangma.cn.service;

import com.shangma.cn.entity.Admin;
import com.shangma.cn.entity.Role;
import com.shangma.cn.entity.Supplier;
import com.shangma.cn.service.base.BaseService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 供应商相关接口
 */
public interface AdminService extends BaseService<Admin>{

    /**
     * 查询所有员工数据
     * @return
     */
    List<Admin> getAllData();

    /**
     * 查询员工数据和角色信息
     * @param id
     * @return
     */
    List<Role> findAdminRoles(Long id);

    /**
     * 添加员工数据和角色信息数据到数据库
     * @param entity
     * @return
     */
    int addAdminAndRoles(Admin entity);

    int updateAdminAndRole(Admin entity);

    int deleteAdminRole(Long adminId, Long roleId);

    Admin existAdminAccount(String adminAccount);

    /**
     * 获取员工信息
     * @return
     */
    Map<String,Object> getAdminInfo();
}
