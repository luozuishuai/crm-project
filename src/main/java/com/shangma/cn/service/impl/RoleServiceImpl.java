package com.shangma.cn.service.impl;

import com.shangma.cn.entity.Menu;
import com.shangma.cn.entity.Role;
import com.shangma.cn.entity.RoleMenuExample;
import com.shangma.cn.entity.RoleMenuKey;
import com.shangma.cn.mapper.MenuMapper;
import com.shangma.cn.mapper.RoleMapper;
import com.shangma.cn.mapper.RoleMenuMapper;
import com.shangma.cn.service.RoleService;
import com.shangma.cn.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.management.relation.RoleInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Role> getAll() {
        return roleMapper.selectByExample(null);
    }

    @Override
    public int addRoleAndMenu(Role entity) {
        addEntity(entity);
        List<Long> menuIds = entity.getMenuIds();
        if (menuIds != null) {
            menuIds.forEach(menuId -> {
                roleMenuMapper.insert(new RoleMenuKey(entity.getId(), menuId));
            });
        }

        return 1;
    }

    /**
     * 查询角色信息和角色菜单权限信息
     *
     * @param id 角色id
     * @return 角色对象
     */
    @Override
    public Role getRoleAndMenuIds(Long id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        RoleMenuExample example = new RoleMenuExample();
        example.createCriteria().andRoleIdEqualTo(id);
        List<RoleMenuKey> roleMenuKeys = roleMenuMapper.selectByExample(example);

        //获取所有菜单信息
        ArrayList<Menu> menus = new ArrayList<>();
        roleMenuKeys.forEach(roleMenuKey -> menus.add(menuMapper.selectByPrimaryKey(roleMenuKey.getMenuId())));
        //先过滤掉目录级别的menu
        List<Menu> menusList = menus.stream().filter(menu -> !menu.getMenuType().equalsIgnoreCase("F")).collect(Collectors.toList());

        //再过滤掉有孩子的
        ArrayList<Long> menuIds = new ArrayList<>();
        menusList.forEach(menu -> {
            if (!hasChildren(menusList, menu)) {
                //如果没有孩子，就添加到数组
                menuIds.add(menu.getId());
            }
        });
        role.setMenuIds(menuIds);
        return role;
    }

    /**
     * 判断是否有孩子
     *
     * @param MenuList
     * @param menu
     * @return
     */
    public boolean hasChildren(List<Menu> MenuList, Menu menu) {
        //判断总列表MenuList中的每一个Menu的父id是否等于menu的id
        //如果相等 说明有孩子
        return MenuList.stream().anyMatch(menu1 -> menu1.getParentId().equals(menu.getId()));
    }

    @Override
    public int updateRoleAndMenu(Role entity) {
        //更新员工信息
        updateEntity(entity);
        //更新菜单权限信息
        RoleMenuExample example = new RoleMenuExample();
        //先删除掉原有的菜单权限信息
        example.createCriteria().andRoleIdEqualTo(entity.getId());
        roleMenuMapper.deleteByExample(example);
        //获取更新的所有菜单权限信息
        List<Long> menuIds = entity.getMenuIds();
        if (!CollectionUtils.isEmpty(menuIds)) {
            menuIds.forEach(menuId -> {
                //添加角色权限
                roleMenuMapper.insert(new RoleMenuKey(entity.getId(), menuId));
            });
        }

        return 1;
    }


    @Override
    public int batchDeleteRoleAndMenuByIds(List<Long> ids) {
        ids.forEach(RoleId -> {
            //删除员工信息
            roleMapper.deleteByPrimaryKey(RoleId);
            //删除菜单信息
            RoleMenuExample example = new RoleMenuExample();
            example.createCriteria().andRoleIdEqualTo(RoleId);
            roleMenuMapper.deleteByExample(example);
        });
        return 1;
    }
}
