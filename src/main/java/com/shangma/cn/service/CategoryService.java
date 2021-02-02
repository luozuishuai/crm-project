package com.shangma.cn.service;

import com.shangma.cn.entity.Brand;
import com.shangma.cn.entity.Category;
import com.shangma.cn.service.base.BaseService;
import com.shangma.cn.vo.PageVo;

import java.util.List;

public interface CategoryService extends BaseService<Category>{

    /**
     * 查询树形菜单
     */
    PageVo<Category> getTreeData();
}
