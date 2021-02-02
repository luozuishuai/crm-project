package com.shangma.cn.controller;

import com.github.pagehelper.PageHelper;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.controller.base.BaseController;
import com.shangma.cn.entity.Brand;
import com.shangma.cn.service.BrandService;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController extends BaseController{


    @Autowired
    private BrandService brandService;

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping
    public AxiosResult<PageVo<Brand>> findPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        PageVo<Brand> pageVo = brandService.findAll();
        return AxiosResult.success(pageVo);
    }

    /**
     * 通过id查询
     */
    @GetMapping("{id}")
    public AxiosResult findById(@PathVariable("id") Long id) {
        return AxiosResult.success(brandService.findById(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    public AxiosResult<Void> insertEntity(@RequestBody Brand entity) {
        //随机生成品牌id
//        entity.setId(UUID.randomUUID().toString().replaceAll("-",""));
        int i = brandService.addEntity(entity);
        return toAxios(i);
    }

    /**
     * 修改数据
     */
    @PutMapping
    public AxiosResult<Void> updateEntity(@RequestBody Brand entity) {
        int i = brandService.updateEntity(entity);
        return toAxios(i);
    }


    /**
     * 批量删除数据
     */
    @DeleteMapping("{ids}")
    public AxiosResult<Void> deleteEntity(@PathVariable("ids") List<Long> ids) {
        int i = brandService.batchDeleteByIds(ids);
        return toAxios(i);
    }

}
