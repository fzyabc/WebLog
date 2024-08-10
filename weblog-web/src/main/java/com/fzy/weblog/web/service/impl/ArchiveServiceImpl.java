package com.fzy.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fzy.weblog.common.domain.dos.*;
import com.fzy.weblog.common.domain.mapper.*;
import com.fzy.weblog.common.enums.ResponseCodeEnum;
import com.fzy.weblog.common.exception.BizException;
import com.fzy.weblog.common.utils.PageResponse;
import com.fzy.weblog.common.utils.Response;
import com.fzy.weblog.web.markdown.MarkdownHelper;
import com.fzy.weblog.web.model.vo.archive.FindArchiveArticlePageListReqVO;
import com.fzy.weblog.web.model.vo.archive.FindArchiveArticlePageListRspVO;
import com.fzy.weblog.web.model.vo.archive.FindArchiveArticleRspVO;
import com.fzy.weblog.web.model.vo.article.FindArticleDetailReqVO;
import com.fzy.weblog.web.model.vo.article.FindArticleDetailRspVO;
import com.fzy.weblog.web.model.vo.article.FindPreNextArticleRspVO;
import com.fzy.weblog.web.model.vo.tag.FindTagListRspVO;
import com.fzy.weblog.web.service.ArchiveService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;

    /**
     * 获取文章归档分页数据
     *
     * @param findArchiveArticlePageListReqVO
     * @return
     */
    @Override
    public Response findArchivePageList(FindArchiveArticlePageListReqVO findArchiveArticlePageListReqVO) {
        Long current = findArchiveArticlePageListReqVO.getCurrent();
        Long size = findArchiveArticlePageListReqVO.getSize();

        // 分页查询
        IPage<ArticleDO> page = articleMapper.selectPageList(current, size, null, null, null);
        List<ArticleDO> articleDOS = page.getRecords();

        List<FindArchiveArticlePageListRspVO> vos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(articleDOS)) {
            // DO 转 VO
           List<FindArchiveArticleRspVO> archiveArticleRspVOS = articleDOS.stream().map(articleDO -> FindArchiveArticleRspVO.builder()
                    .id(articleDO.getId())
                    .title(articleDO.getTitle())
                    .cover(articleDO.getCover())
                    .createDate(articleDO.getCreateTime().toLocalDate())
                    .createMonth(YearMonth.from(articleDO.getCreateTime()))
                    .build()).collect(Collectors.toList());

            // 按创建的月份进行分组
            Map<YearMonth, List<FindArchiveArticleRspVO>> map = archiveArticleRspVOS.stream().collect(Collectors.groupingBy(FindArchiveArticleRspVO::getCreateMonth));
            // 使用 TreeMap 按月份倒序排列
            Map<YearMonth, List<FindArchiveArticleRspVO>> sortedMap = new TreeMap<>(Collections.reverseOrder());
            sortedMap.putAll(map);

            // 遍历排序后的 Map，将其转换为归档 VO
            sortedMap.forEach((k, v) -> vos.add(FindArchiveArticlePageListRspVO.builder().month(k).articles(v).build()));
        }

        return PageResponse.success(page, vos);
    }


}