package com.fzy.weblog.admin.service.impl;

import com.fzy.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.fzy.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.fzy.weblog.admin.service.AdminCategoryService;
import com.fzy.weblog.common.domain.dos.CategoryDO;
import com.fzy.weblog.common.domain.mapper.CategoryMapper;
import com.fzy.weblog.common.enums.ResponseCodeEnum;
import com.fzy.weblog.common.exception.BizException;
import com.fzy.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public Response addCategory(AddCategoryReqVO addCategoryReqVO) {
        String categoryName = addCategoryReqVO.getName();
        CategoryDO categoryDO = categoryMapper.selectByName(categoryName);
        if (Objects.nonNull(categoryDO)){
            log.warn("分类名称： {}, 此分类已存在", categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);

        }
        //如果不存在添加分类
        CategoryDO insertCategoryDO = CategoryDO.builder().name(categoryName.trim()).build();
        categoryMapper.insert(insertCategoryDO);
        return Response.success();
    }

    @Override
    public Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO) {
        Long categoryId = deleteCategoryReqVO.getId();
        //删除分类
        categoryMapper.deleteById(categoryId);
        return Response.success();
    }
}
