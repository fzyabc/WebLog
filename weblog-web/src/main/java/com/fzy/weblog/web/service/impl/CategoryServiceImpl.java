package com.fzy.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzy.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.fzy.weblog.common.domain.dos.ArticleDO;
import com.fzy.weblog.common.domain.dos.CategoryDO;
import com.fzy.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.fzy.weblog.common.domain.mapper.ArticleMapper;
import com.fzy.weblog.common.domain.mapper.CategoryMapper;
import com.fzy.weblog.common.enums.ResponseCodeEnum;
import com.fzy.weblog.common.exception.BizException;
import com.fzy.weblog.common.utils.Response;
import com.fzy.weblog.web.model.vo.category.FindCategoryArticlePageListReqVO;
import com.fzy.weblog.web.model.vo.category.FindCategoryArticlePageListRspVO;
import com.fzy.weblog.web.model.vo.category.FindCategoryListRspVO;
import com.fzy.weblog.web.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public Response findCategoryList() {
        // 查询所有分类
        List<CategoryDO> categoryDOS = categoryMapper.selectList(Wrappers.emptyWrapper());
        // DO 转 VO
        List<FindCategoryListRspVO> vos=null;
       if (!CollectionUtils.isEmpty(categoryDOS)){
vos=categoryDOS.stream().map(categoryDO -> FindCategoryListRspVO.builder()
        .id(categoryDO.getId())
        .name(categoryDO.getName())
        .build()).collect(Collectors.toList());
       }
        return Response.success(vos);
    }

    @Override
    public Response findCategoryArticlePageList(FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO) {
        Long current = findCategoryArticlePageListReqVO.getCurrent();
        Long size = findCategoryArticlePageListReqVO.getSize();
        Long categoryId = findCategoryArticlePageListReqVO.getId();
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)){
            log.warn("==> 该分类不存在, categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }
        // 先查询该分类下所有关联的文章 ID
        List<ArticleCategoryRelDO> articleCategoryRelDOS = articleCategoryRelMapper.selectListByCategoryId(categoryId);
        List<Long> articleIds = articleCategoryRelDOS.stream().map(ArticleCategoryRelDO::getArticleId).collect(Collectors.toList());
        // 根据文章 ID 集合查询文章分页数据
        Page<ArticleDO> articleDOPage=articleMapper.selectPageListByArticleIds(current,size,articleIds);
        List<ArticleDO> articleDOS = articleDOPage.getRecords();
        // DO 转 VO
        List<FindCategoryArticlePageListRspVO> vos=null;
        if (!CollectionUtils.isEmpty(articleDOS)){
             vos=articleDOS.stream().map(articleDO -> FindCategoryArticlePageListRspVO.builder()
                    .cover(articleDO.getCover())
                    .createDate(articleDO.getCreateTime().toLocalDate())
                    .id(articleDO.getId())
                    .title(articleDO.getTitle())
                   .build()).collect(Collectors.toList());
               }
        return Response.success(vos);
    }
}
