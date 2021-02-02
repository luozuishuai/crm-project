package com.shangma.cn.service.base.impl;

import com.github.pagehelper.PageInfo;
import com.shangma.cn.mapper.mapper.BaseMapper;
import com.shangma.cn.service.base.BaseService;
import com.shangma.cn.common.untils.ReflectionUtils;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class BaseServiceImpl<T> implements BaseService<T>{

    @Autowired
    private BaseMapper<T> baseMapper;

    @Override
    public PageVo<T> findAll() {
        List<T> list = baseMapper.selectByExample(null);
        return setPageVo(list);
    }

    @Override
    public PageVo<T> findAll(Object example) {
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
    public T findById(Long id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addEntity(T entity) {
        ReflectionUtils.invokeMethod(entity,"doSetDate",null,null);
        return baseMapper.insert(entity);

    }

    @Override
    public int updateEntity(T entity) {
        ReflectionUtils.invokeMethod(entity,"doSetDate",null,null);
        return baseMapper.updateByPrimaryKey(entity);
    }

    @Override
    public int deleteEntity(Long id) {
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
    public int batchDeleteByIds(List<Long> ids) {
        ids.forEach(item -> baseMapper.deleteByPrimaryKey(item));
        return 1;
    }


}
