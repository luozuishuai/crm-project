package com.shangma.cn.controller;

import com.github.pagehelper.PageHelper;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.controller.base.BaseController;
import com.shangma.cn.entity.Brand;
import com.shangma.cn.entity.Category;
import com.shangma.cn.service.CategoryService;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController extends BaseController{

    @Autowired
    private CategoryService categoryService;

//    @GetMapping("getTreeData")
//    public AxiosResult<List<Category>> getTreeData(){
//        return AxiosResult.success(categoryService.getTreeData());
//    }

    @GetMapping("getTreeData")
    public AxiosResult<PageVo<Category>> findPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "5") int pageSize){
        PageHelper.startPage(currentPage,pageSize);
        PageVo<Category> page = categoryService.getTreeData();
        return AxiosResult.success(page);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public AxiosResult<Category> findById(@PathVariable Long id){
        return AxiosResult.success(categoryService.findById(id));
    }

    @PostMapping
    public AxiosResult<Void> addEntity(@RequestBody Category entity){
        return toAxios(categoryService.addEntity(entity));
    }

    @PutMapping
    public AxiosResult<Void> updateEntity(@RequestBody Category entity){
        return toAxios(categoryService.updateEntity(entity));
    }

//    @DeleteMapping("{id}")
//    public AxiosResult<Void> deleteById(@PathVariable Long id){
//        return toAxios(categoryService.deleteEntity(id));
//    }

    /**
     * 批量删除数据
     */
    @DeleteMapping("{ids}")
    public AxiosResult<Void> deleteEntity(@PathVariable("ids") List<Long> ids) {
        int i = categoryService.batchDeleteByIds(ids);
        return toAxios(i);
    }

}
