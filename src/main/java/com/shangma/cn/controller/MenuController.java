package com.shangma.cn.controller;

import com.github.pagehelper.PageHelper;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.controller.base.BaseController;
import com.shangma.cn.entity.Menu;
import com.shangma.cn.entity.Supplier;
import com.shangma.cn.service.MenuService;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("menu")
public class MenuController extends BaseController{


    @Autowired
    private MenuService menuService;

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping
    public AxiosResult<PageVo<Menu>> findPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        PageVo<Menu> pageVo = menuService.findAll();
        return AxiosResult.success(pageVo);
    }

    /**
     * 获取树形数据
     */
    @GetMapping("getAllMenuTreeData")
    public AxiosResult<List<Menu>> getAllMenuTreeData(){
        List<Menu> rootList = menuService.getAllMenuTreeData();
        return AxiosResult.success(rootList);
    }


    /**
     * 获取树形数据
     */
    @GetMapping("getTreeData")
    public AxiosResult<PageVo<Menu>> getTreeData(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "5") int pageSize){
        PageHelper.startPage(currentPage,pageSize);
        PageVo<Menu> page = menuService.getTreeData();
        return AxiosResult.success(page);
    }



    /**
     * 通过id查询
     */
    @GetMapping("{id}")
    public AxiosResult<Menu> findById(@PathVariable("id") Long id) {
        return AxiosResult.success(menuService.findById(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    public AxiosResult<Void> insertEntity(@RequestBody Menu entity) {
        int i = menuService.addEntity(entity);
        return toAxios(i);
    }

    /**
     * 修改数据
     */
    @PutMapping
    public AxiosResult<Void> updateEntity(@RequestBody Menu entity) {
        int i = menuService.updateEntity(entity);
        return toAxios(i);
    }


    /**
     * 批量删除数据
     */
    @DeleteMapping("{ids}")
    public AxiosResult<Void> deleteEntity(@PathVariable("ids") List<Long> ids) {
        int i = menuService.batchDeleteMenuAndRoleByIds(ids);
        return toAxios(i);
    }

}
