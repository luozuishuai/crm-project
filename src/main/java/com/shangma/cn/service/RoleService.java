package com.shangma.cn.service;

import com.shangma.cn.entity.Role;
import com.shangma.cn.entity.Supplier;
import com.shangma.cn.service.base.BaseService;
import com.shangma.cn.vo.PageVo;

import java.util.List;

/**
 * 供应商相关接口
 */
public interface RoleService extends BaseService<Role>{

    /**
     * 查询所有角色
     * @return
     */
    List<Role> getAll();

    /**
     * 添加角色和对应的菜单权限信息
     * @param entity
     * @return
     */
    int addRoleAndMenu(Role entity);

    /**
     * 获取角色和权限信息
     * @param id
     * @return
     */
    Role getRoleAndMenuIds(Long id);

    /**
     * 更新角色信息和菜单权限信息
     * @param entity
     * @return
     */
    int updateRoleAndMenu(Role entity);

    /**
     * 批量删除角色和菜单权限信息
     * @param ids
     * @return
     */
    int batchDeleteRoleAndMenuByIds(List<Long> ids);
}
