package com.shangma.cn.service.base;

import com.shangma.cn.vo.PageVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseService <T>{

    /**
     * 分页查询
     * @return
     */
    PageVo<T> findAll();

    /**
     * 有条件的分页查询
     */
    PageVo<T> findAll(Object example);

    /**
     * 封装分页数据
     */
    PageVo<T> setPageVo(List<T> list);

    /**
     * 通过id查询
     */
    T findById(Long id);

    /**
     * 添加操作
     */
    int addEntity(T entity);

    /**
     * 更新操作
     */
    int updateEntity(T entity);

    /**
     * 删除操作
     */
    int deleteEntity(Long id);

    /**
     * 批量添加
     */
    int batchAddEntity(List<T> list);

    /**
     * 批量修改
     */
    int batchUpdateEntity(List<T> list);

    /**
     * 批量删除
     */
    int batchDeleteByIds(List<Long> ids);


}
