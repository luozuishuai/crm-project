package com.shangma.cn.service;

import com.shangma.cn.entity.Menu;
import com.shangma.cn.entity.Supplier;
import com.shangma.cn.service.base.BaseService;
import com.shangma.cn.vo.PageVo;

import java.util.List;

/**
 * 供应商相关接口
 */
public interface MenuService extends BaseService<Menu>{

    /**
     * 获取树形数据
     * @return
     */
    PageVo<Menu> getTreeData();

    /**
     * 获取所有树形数据
     * @return
     */
    List<Menu> getAllMenuTreeData();

    /**
     * 批量删除菜单和角色有该菜单信息
     * @param ids
     * @return
     */
    int batchDeleteMenuAndRoleByIds(List<Long> ids);
}
