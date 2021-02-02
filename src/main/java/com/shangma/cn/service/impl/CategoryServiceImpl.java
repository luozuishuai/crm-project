package com.shangma.cn.service.impl;

import com.shangma.cn.entity.Category;
import com.shangma.cn.entity.CategoryExample;
import com.shangma.cn.mapper.CategoryMapper;
import com.shangma.cn.service.CategoryService;
import com.shangma.cn.service.base.impl.BaseServiceImpl;
import com.shangma.cn.common.untils.TreeDataUtils;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 原代码
     */
    @Override
    public PageVo<Category> getTreeData() {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(0l);
        List<Category> root = categoryMapper.selectByExample(example);
        List<Category> all = categoryMapper.selectByExample(null);

        TreeDataUtils.buildTreeData(all,root);

        return setPageVo(root);
    }

}
