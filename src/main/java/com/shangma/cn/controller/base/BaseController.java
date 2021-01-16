package com.shangma.cn.controller.base;

import com.github.pagehelper.PageHelper;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.service.base.BaseService;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


public class BaseController<T, ID> {

    @Autowired
    private BaseService<T, ID> baseService;

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping
    public AxiosResult<PageVo<T>> findPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        PageVo<T> pageVo = baseService.findPage();
        return AxiosResult.success(pageVo);
    }

    /**
     * 通过id查询
     */
    @GetMapping("{id}")
    public AxiosResult<T> findById(@PathVariable("id") ID id) {
        return AxiosResult.success(baseService.findById(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    public AxiosResult<Void> insertEntity(@RequestBody T entity) {
        int i = baseService.addEntity(entity);
        return toAxios(i);
    }

    /**
     * 修改数据
     */
    @PutMapping
    public AxiosResult<Void> updateEntity(@RequestBody T entity) {
        int i = baseService.updateEntity(entity);
        return toAxios(i);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("{id}")
    public AxiosResult<Void> deleteEntity(@PathVariable("id") ID id) {
        int i = baseService.deleteEntity(id);
        return toAxios(i);
    }

    /**
     * 判断影响数据行数是否大于0
     */
    public AxiosResult<Void> toAxios(int row) {
        return row > 0 ? AxiosResult.success() : AxiosResult.error();
    }
}
