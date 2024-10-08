package com.fzy.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzy.weblog.admin.model.vo.tag.*;
import com.fzy.weblog.admin.service.AdminTagService;
import com.fzy.weblog.common.domain.dos.ArticleTagRelDO;
import com.fzy.weblog.common.domain.dos.TagDO;
import com.fzy.weblog.common.domain.mapper.ArticleTagRelMapper;
import com.fzy.weblog.common.domain.mapper.TagMapper;
import com.fzy.weblog.common.enums.ResponseCodeEnum;
import com.fzy.weblog.common.exception.BizException;
import com.fzy.weblog.common.utils.PageResponse;
import com.fzy.weblog.common.utils.Response;
import com.fzy.weblog.model.vo.SelectRspVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO>implements AdminTagService {
@Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;

    @Override
    public Response addTags(AddTagReqVO addTagReqVO) {
    List<TagDO> tagDOList = addTagReqVO.getTags().stream().map(tagName -> TagDO.builder()
                    .name(tagName.trim())
            .createTime(LocalDateTime.now())
            .updateTime(LocalDateTime.now())
            .build()).collect(Collectors.toList());


try {
    this.saveBatch(tagDOList);
}catch (Exception e){
    log.warn("标签已存在");
}



        return Response.success();
    }

    @Override
    public PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO) {
Long current = findTagPageListReqVO.getCurrent();
Long size = findTagPageListReqVO.getSize();
String name= findTagPageListReqVO.getName();
        LocalDate startDate = findTagPageListReqVO.getStartDate();
        LocalDate endDate = findTagPageListReqVO.getEndDate();
        Page<TagDO> tagDOPage = tagMapper.selectPageList(current, size, name, startDate, endDate);
        List<TagDO> records = tagDOPage.getRecords();
        List<FindTagPageListRspVO> vos=null;
if (!CollectionUtils.isEmpty(records)){
vos=records.stream().map(tagDO -> FindTagPageListRspVO.builder()
        .id(tagDO.getId())
        .name(tagDO.getName())
        .createTime(tagDO.getCreateTime())
        .build()).collect(Collectors.toList());

}
        return PageResponse.success(tagDOPage,vos);
    }

    @Override
    public Response deleteTag(DeleteTagReqVO deleteTagReqVO) {
        Long tagId=deleteTagReqVO.getId();
        ArticleTagRelDO articleTagRelDO = articleTagRelMapper.selectOneByTagId(tagId);
        if (Objects.nonNull(articleTagRelDO)){
            log.warn("==> 此标签下包含文章，无法删除，tagId: {}", tagId);
            throw new BizException(ResponseCodeEnum.TAG_CAN_NOT_DELETE);
        }
        int count=tagMapper.deleteById(tagId);
        return count==1?Response.success("删除成功"):Response.fail(ResponseCodeEnum.TAG_NOT_EXISTED);
    }

//    @Override
//    public Response findTagSelectList() {
//        //查询所有标签
//        List<TagDO> tagDOList = tagMapper.selectList(null);
//        //DO转VO
//        List<FindTagPageListRspVO> vos=null;
//        if (!CollectionUtils.isEmpty(tagDOList)){
//            vos=tagDOList.stream().map(tagDO -> FindTagPageListRspVO.builder()
//
//                    .id())
//        }
//
//
//        return null;
//    }

    @Override
    public Response searchTag(SearchTagReqVO searchTagReqVO) {
        String key=searchTagReqVO.getKey();
        List<TagDO> tagDOS = tagMapper.selectByKey(key);
        List<SelectRspVO> vos=null;
        vos=tagDOS.stream().map(tagDO -> SelectRspVO.builder()

                .label(tagDO.getName()).value(tagDO.getId())
                .build()).collect(Collectors.toList());

        return Response.success(vos);
    }

    @Override
    public Response findTagSelectList() {
        // 查询所有标签, Wrappers.emptyWrapper() 表示查询条件为空
        List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());

        // DO 转 VO
        List<SelectRspVO> vos = null;
        if (!CollectionUtils.isEmpty(tagDOS)) {
            vos = tagDOS.stream()
                    .map(tagDO -> SelectRspVO.builder()
                            .label(tagDO.getName())
                            .value(tagDO.getId())
                            .build())
                    .collect(Collectors.toList());
        }

        return Response.success(vos);
    }


}