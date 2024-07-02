package com.fzy.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzy.weblog.common.domain.dos.CategoryDO;

/**
 根据用户名查询
 * @param categoryName
 * @return
 */
public interface CategoryMapper extends BaseMapper<CategoryDO> {
    default CategoryDO selectByName(String categoryName) {
        LambdaQueryWrapper<CategoryDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CategoryDO::getName, categoryName);
        return selectOne(lambdaQueryWrapper);
    }
}
