package com.fzy.weblog.admin.service;

import com.fzy.weblog.admin.model.vo.tag.AddTagReqVO;
import com.fzy.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.fzy.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.fzy.weblog.admin.model.vo.tag.SearchTagReqVO;
import com.fzy.weblog.common.utils.PageResponse;
import com.fzy.weblog.common.utils.Response;

/**
 * @author: FZY
 * @date: 2021/7/16
 * @description:
 *
 */
public interface AdminTagService {
    /**
     * 添加标签集合
     * @param addTagReqVO
     * @return
     */

    Response addTags(AddTagReqVO addTagReqVO);

    /**
     * 查询标签分页
     * @param findTagPageListReqVO
     * @return
     */
    PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO);

    Response deleteTag(DeleteTagReqVO deleteTagReqVO);

//    Response findTagSelectList();

    /**
     * 根据标签关键字模糊查询
     * @param searchTagReqVO
     * @return
     */
    Response searchTag(SearchTagReqVO searchTagReqVO);

    /**
     * 查询标签 Select 列表数据
     * @return
     */
    Response findTagSelectList();
}