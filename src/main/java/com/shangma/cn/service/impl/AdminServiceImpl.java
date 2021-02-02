package com.shangma.cn.service.impl;

import com.shangma.cn.common.service.TokenService;
import com.shangma.cn.common.untils.ServletUtils;
import com.shangma.cn.common.untils.TreeDataUtils;
import com.shangma.cn.entity.*;
import com.shangma.cn.mapper.*;
import com.shangma.cn.service.AdminService;
import com.shangma.cn.service.SupplierService;
import com.shangma.cn.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Admin> getAllData() {
        return adminMapper.selectByExample(null);
    }

    @Override
    public List<Role> findAdminRoles(Long id) {
        AdminRoleExample example = new AdminRoleExample();
        example.createCriteria().andAdminIdEqualTo(id);
        List<AdminRoleKey> roleKeys = adminRoleMapper.selectByExample(example);
        ArrayList<Role> roles = new ArrayList<>();
        roleKeys.forEach(roleKey -> {
            Long roleId = roleKey.getRoleId();
            Role role = roleMapper.selectByPrimaryKey(roleId);
            roles.add(role);
        });
        return roles;
    }

    @Override
    public int addAdminAndRoles(Admin entity) {
        //添加员工信息到数据库
        addEntity(entity);
        //添加角色信息
        List<Long> roleIds = entity.getRoleIds();
        roleIds.forEach(roleId -> {
            adminRoleMapper.insert(new AdminRoleKey(entity.getId(), roleId));
        });

        return 1;
    }

    @Override
    public int updateAdminAndRole(Admin entity) {
        //更新员工信息
        updateEntity(entity);
        //更新角色信息
        List<Long> roleIds = entity.getRoleIds();
        roleIds.forEach(roleId -> {
            adminRoleMapper.insert(new AdminRoleKey(entity.getId(), roleId));
        });

        return 1;
    }

    @Override
    public int deleteAdminRole(Long adminId, Long roleId) {
        AdminRoleExample example = new AdminRoleExample();
        example.createCriteria().andAdminIdEqualTo(adminId).andRoleIdEqualTo(roleId);
        int i = adminRoleMapper.deleteByExample(example);
        return i;
    }

    @Override
    public Admin existAdminAccount(String adminAccount) {
        AdminExample example = new AdminExample();
        example.createCriteria().andAdminAccountEqualTo(adminAccount);
        List<Admin> admins = adminMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(admins)) {
            return admins.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> getAdminInfo() {
        HttpServletRequest request = ServletUtils.getRequest();
        Long adminId = tokenService.getAdminId(request);
        //获取用户信息
        Admin admin = findById(adminId);
        HashMap<String, Object> map = new HashMap<>();
        //获取用户角色信息
        List<Role> adminRoles = findAdminRoles(adminId);
        //通过用户的角色 获取用户的菜单权限
        //创建一个set数组用于存放用户的菜单权限(set去重复权限)
        HashSet<Menu> set = new HashSet<>();
        adminRoles.forEach(adminRole -> {
            RoleMenuExample example = new RoleMenuExample();
            example.createCriteria().andRoleIdEqualTo(adminRole.getId());
            //查询出用户的所有角色
            List<RoleMenuKey> roleMenuKeys = roleMenuMapper.selectByExample(example);
            roleMenuKeys.forEach(roleMenuKey -> {
                //根据用户的每一个角色 查询出角色对应的菜单权限信息
                Menu menu = menuMapper.selectByPrimaryKey(roleMenuKey.getMenuId());
                //添加菜单权限信息到menuList数组
                set.add(menu);
            });

        });


        //排除按钮级别权限
        List<Menu> fmMenus = set.stream().filter(menu ->
                !menu.getMenuType().equalsIgnoreCase("B")
        ).collect(Collectors.toList());

        //拿到一级分类
        List<Menu> root = set.stream().filter(menu -> menu.getParentId().equals(0L)).collect(Collectors.toList());

        TreeDataUtils.buildTreeData(fmMenus, root);


        map.put("menu", root);
        map.put("admin", admin);
        return map;
    }
}
