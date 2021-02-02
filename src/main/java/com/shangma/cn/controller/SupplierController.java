package com.shangma.cn.controller;

import com.github.pagehelper.PageHelper;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.controller.base.BaseController;
import com.shangma.cn.entity.Supplier;
import com.shangma.cn.service.SupplierService;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("supplier")
public class SupplierController extends BaseController{


    @Autowired
    private SupplierService supplierService;

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping
    public AxiosResult<PageVo<Supplier>> findPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        PageVo<Supplier> pageVo = supplierService.findAll();
        return AxiosResult.success(pageVo);
    }

    /**
     * 通过id查询
     */
    @GetMapping("{id}")
    public AxiosResult<Supplier> findById(@PathVariable("id") Long id) {
        return AxiosResult.success(supplierService.findById(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    public AxiosResult<Void> insertEntity(@RequestBody Supplier entity) {
        int i = supplierService.addEntity(entity);
        return toAxios(i);
    }

    /**
     * 修改数据
     */
    @PutMapping
    public AxiosResult<Void> updateEntity(@RequestBody Supplier entity) {
        int i = supplierService.updateEntity(entity);
        return toAxios(i);
    }


    /**
     * 批量删除数据
     */
    @DeleteMapping("{ids}")
    public AxiosResult<Void> deleteEntity(@PathVariable("ids") List<Long> ids) {
        int i = supplierService.batchDeleteByIds(ids);
        return toAxios(i);
    }

}
