package com.fzy.weblog.common.domain.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzy.weblog.common.domain.dos.CommentDO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface CommentMapper extends BaseMapper<CommentDO> {
    /**
     * 根据路由地址、状态查询对应的评论
     * @param routerUrl
     * @return
     */
    default List<CommentDO> selectByRouterUrlAndStatus(String routerUrl, Integer status){
return selectList(Wrappers.<CommentDO>lambdaQuery()
        .eq(CommentDO::getRouterUrl, routerUrl)
        .eq(CommentDO::getStatus, status)
        .orderByDesc(CommentDO::getCreateTime));
    }
    /**
     * 分页查询
     * @param current
     * @param size
     * @param startDate
     * @param endDate
     * @return
     */
    default Page<CommentDO> selectPageList(Long current, Long size, String routerUrl, LocalDate startDate, LocalDate endDate, Integer status){
Page<CommentDO> page = new Page<>(current, size);
        LambdaQueryWrapper<CommentDO> wrapper=Wrappers.<CommentDO>lambdaQuery()
                .like(StringUtils.isNotBlank(routerUrl), CommentDO::getRouterUrl, routerUrl)
                .eq(Objects.nonNull(status), CommentDO::getStatus, status)
                .ge(Objects.nonNull(startDate), CommentDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), CommentDO::getCreateTime, endDate)
                .orderByDesc(CommentDO::getCreateTime);
        return selectPage(page, wrapper);
    }
    /**
     * 根据 reply_comment_id 查询评论
     * @param replyCommentId
     * @return
     */
    default List<CommentDO> selectByReplyCommentId(Long replyCommentId){
return selectList(Wrappers.<CommentDO>lambdaQuery()
        .eq(CommentDO::getReplyCommentId, replyCommentId));
    }
    /**
     * 根据 parent_comment_id 删除
     * @param id
     * @return
     */
    default int deleteByParentCommentId(Long id){
return delete(Wrappers.<CommentDO>lambdaQuery()
        .eq(CommentDO::getParentCommentId, id));
    }
}