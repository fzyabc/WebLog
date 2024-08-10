package com.fzy.weblog.web.service;

import com.fzy.weblog.common.utils.Response;
import com.fzy.weblog.web.model.vo.archive.FindArchiveArticlePageListReqVO;
import com.fzy.weblog.web.model.vo.article.FindArticleDetailReqVO;


public interface ArchiveService {
    /**
     * 获取文章归档分页数据
     * @param findArchiveArticlePageListReqVO
     * @return
     */
    Response findArchivePageList(FindArchiveArticlePageListReqVO findArchiveArticlePageListReqVO);

}