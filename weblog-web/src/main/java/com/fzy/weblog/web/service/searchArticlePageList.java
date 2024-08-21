package com.fzy.weblog.web.service;

import com.fzy.weblog.common.utils.Response;
import com.fzy.weblog.web.model.vo.search.SearchArticlePageListReqVO;

public interface searchArticlePageList {
    /**
     * 关键词分页搜索
     * @param searchArticlePageListReqVO
     * @return
     */
    Response searchArticlePageList(SearchArticlePageListReqVO searchArticlePageListReqVO);
}
