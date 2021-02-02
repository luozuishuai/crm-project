package com.shangma.cn.service.impl;

import com.shangma.cn.common.untils.TreeDataUtils;
import com.shangma.cn.entity.Menu;
import com.shangma.cn.entity.MenuExample;
import com.shangma.cn.entity.RoleMenuExample;
import com.shangma.cn.entity.Supplier;
import com.shangma.cn.mapper.MenuMapper;
import com.shangma.cn.mapper.RoleMenuMapper;
import com.shangma.cn.service.MenuService;
import com.shangma.cn.service.SupplierService;
import com.shangma.cn.service.base.impl.BaseServiceImpl;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public PageVo<Menu> getTreeData() {
        MenuExample example = new MenuExample();
        example.createCriteria().andParentIdEqualTo(0L);
        List<Menu> rootList = menuMapper.selectByExample(example);
        List<Menu> allList = menuMapper.selectByExample(null);

        TreeDataUtils.buildTreeData(allList,rootList);

        return setPageVo(rootList);
    }

    @Override
    public List<Menu> getAllMenuTreeData() {
        MenuExample example = new MenuExample();
        example.createCriteria().andParentIdEqualTo(0L);
        List<Menu> rootList = menuMapper.selectByExample(example);
        List<Menu> allList = menuMapper.selectByExample(null);

        TreeDataUtils.buildTreeData(allList,rootList);
        return rootList;
    }

    @Override
    public int batchDeleteMenuAndRoleByIds(List<Long> ids) {
        batchDeleteByIds(ids);
        ids.forEach(id -> {
            RoleMenuExample example = new RoleMenuExample();
            example.createCriteria().andMenuIdEqualTo(id);
            roleMenuMapper.deleteByExample(example);
        });
        return 1;
    }
}
