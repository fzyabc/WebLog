package com.fzy.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzy.weblog.common.domain.dos.ArticleDO;
import com.fzy.weblog.common.domain.dos.ArticleTagRelDO;
import com.fzy.weblog.common.domain.dos.TagDO;
import com.fzy.weblog.common.domain.mapper.ArticleMapper;
import com.fzy.weblog.common.domain.mapper.ArticleTagRelMapper;
import com.fzy.weblog.common.domain.mapper.TagMapper;
import com.fzy.weblog.common.enums.ResponseCodeEnum;
import com.fzy.weblog.common.exception.BizException;
import com.fzy.weblog.common.utils.PageResponse;
import com.fzy.weblog.common.utils.Response;
import com.fzy.weblog.web.model.vo.tag.FindTagArticlePageListReqVO;
import com.fzy.weblog.web.model.vo.tag.FindTagArticlePageListRspVO;
import com.fzy.weblog.web.model.vo.tag.FindTagListRspVO;
import com.fzy.weblog.web.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取标签列表
     *
     * @return
     */
    @Override
    public Response findTagList() {
        //先查询所有标签
        List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());
        //DO转VO
        List<FindTagListRspVO> vos=null;
        if (!CollectionUtils.isEmpty(tagDOS)){
            vos=tagDOS.stream().map(tagDO -> FindTagListRspVO.builder()
                    .id(tagDO.getId()).
                    name(tagDO.getName()).
                    build()).collect(Collectors.toList());

        }
        return Response.success(vos);
    }

    @Override
    public Response findTagPageList(FindTagArticlePageListReqVO findTagArticlePageListReqVO) {
        Long current = findTagArticlePageListReqVO.getCurrent();
        Long size = findTagArticlePageListReqVO.getSize();
        // 标签 ID
        Long tagId = findTagArticlePageListReqVO.getId();

        // 判断该标签是否存在
        TagDO tagDO = tagMapper.selectById(tagId);
        if (Objects.isNull(tagDO)) {
            log.warn("==> 该标签不存在, tagId: {}", tagId);
            throw new BizException(ResponseCodeEnum.TAG_NOT_EXISTED);
        }

        // 先查询该标签下所有关联的文章 ID
        List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByTagId(tagId);

        // 若该标签下未发布任何文章
        if (CollectionUtils.isEmpty(articleTagRelDOS)) {
            log.info("==> 该标签下还未发布任何文章, tagId: {}", tagId);
            return PageResponse.success(null, null);
        }

        // 提取所有文章 ID
        List<Long> articleIds = articleTagRelDOS.stream().map(ArticleTagRelDO::getArticleId).collect(Collectors.toList());

        // 根据文章 ID 集合查询文章分页数据
        Page<ArticleDO> page = articleMapper.selectPageListByArticleIds(current, size, articleIds);
        List<ArticleDO> articleDOS = page.getRecords();

        // DO 转 VO
        List<FindTagArticlePageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(articleDOS)) {
            vos = articleDOS.stream().map(articleDO -> FindTagArticlePageListRspVO.builder()
                    .cover(articleDO.getCover())
                    .createDate(articleDO.getCreateTime().toLocalDate())
                    .id(articleDO.getId())
                    .title(articleDO.getTitle())
                    .build()).collect(Collectors.toList());
        }
        return PageResponse.success(page, vos);
    }
}
