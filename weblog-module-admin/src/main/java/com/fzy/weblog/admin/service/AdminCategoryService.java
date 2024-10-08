package com.fzy.weblog.admin.service;

import com.fzy.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.fzy.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.fzy.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.fzy.weblog.common.utils.PageResponse;
import com.fzy.weblog.common.utils.Response;

public interface AdminCategoryService {
    /**
     * 添加分类
     * @param addCategoryReqVO
     * @return
     */
    Response addCategory(AddCategoryReqVO addCategoryReqVO);
    /**
     * 删除分类
     * @param id
     * @return
     */

    Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO);
    /**
     * 查询分类下拉列表
     * @return
     */
    Response findCategorySelectList();


    PageResponse findCategoryPageList(FindCategoryPageListReqVO findCategoryPageListReqVO);
}