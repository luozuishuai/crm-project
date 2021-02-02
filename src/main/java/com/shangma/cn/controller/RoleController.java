package com.shangma.cn.controller;

import com.github.pagehelper.PageHelper;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.controller.base.BaseController;
import com.shangma.cn.entity.Menu;
import com.shangma.cn.entity.Role;
import com.shangma.cn.service.MenuService;
import com.shangma.cn.service.RoleService;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("role")
public class RoleController extends BaseController{


    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 查询所有角色
     */
    @GetMapping("getAll")
    public AxiosResult<List<Role>> getAll(){
        List<Role> list = roleService.getAll();
        return AxiosResult.success(list);
    }



    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping
    public AxiosResult<PageVo<Role>> findPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        PageVo<Role> pageVo = roleService.findAll();
        return AxiosResult.success(pageVo);
    }

    /**
     * 通过id查询
     */
    @GetMapping("{id}")
    public AxiosResult<Role> findById(@PathVariable("id") Long id) {
        Role role = roleService.getRoleAndMenuIds(id);
        return AxiosResult.success(role);
    }

    /**
     * 新增数据
     */
    @PostMapping
    public AxiosResult<Void> insertEntity(@RequestBody Role entity) {
        int i = roleService.addRoleAndMenu(entity);
        return toAxios(i);
    }

    /**
     * 修改数据
     */
    @PutMapping
    public AxiosResult<Void> updateEntity(@RequestBody Role entity) {
        int i = roleService.updateRoleAndMenu(entity);
        return toAxios(i);
    }


    /**
     * 批量删除数据
     */
    @DeleteMapping("{ids}")
    public AxiosResult<Void> deleteEntity(@PathVariable("ids") List<Long> ids) {
        int i = roleService.batchDeleteRoleAndMenuByIds(ids);
        return toAxios(i);
    }

}
