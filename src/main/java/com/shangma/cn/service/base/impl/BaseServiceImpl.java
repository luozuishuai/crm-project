package com.shangma.cn.service.base.impl;

import com.github.pagehelper.PageInfo;
import com.shangma.cn.mapper.mapper.BaseMapper;
import com.shangma.cn.service.base.BaseService;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class BaseServiceImpl<T,ID> implements BaseService<T,ID>{

    @Autowired
    private BaseMapper<T,ID> baseMapper;

    @Override
    public PageVo<T> findPage() {
        List<T> list = baseMapper.selectByExample(null);
        return setPageVo(list);
    }

    @Override
    public PageVo<T> findPage(Object example) {
        List<T> list = baseMapper.selectByExample(example);
        return setPageVo(list);
    }

    @Override
    public PageVo<T> setPageVo(List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setList(list);
        pageVo.setTotal(total);
        return pageVo;
    }

    @Override
    public T findById(ID id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addEntity(T entity) {
        return baseMapper.insert(entity);
    }

    @Override
    public int updateEntity(T entity) {
        return baseMapper.updateByPrimaryKey(entity);
    }

    @Override
    public int deleteEntity(ID id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int batchAddEntity(List<T> list) {
        list.forEach(item -> baseMapper.insert(item));
        return 1;
    }

    @Override
    @Transactional
    public int batchUpdateEntity(List<T> list) {
        list.forEach(item -> baseMapper.updateByPrimaryKey(item));
        return 1;
    }

    @Override
    @Transactional
    public int batchDeleteByIds(List<ID> ids) {
        ids.forEach(item -> baseMapper.deleteByPrimaryKey(item));
        return 1;
    }
}
