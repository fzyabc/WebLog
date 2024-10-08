package com.fzy.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzy.weblog.admin.event.UpdateCommentEvent;
import com.fzy.weblog.admin.model.vo.comment.DeleteCommentReqVO;
import com.fzy.weblog.admin.model.vo.comment.ExamineCommentReqVO;
import com.fzy.weblog.admin.model.vo.comment.FindCommentPageListReqVO;
import com.fzy.weblog.admin.model.vo.comment.FindCommentPageListRspVO;
import com.fzy.weblog.admin.service.AdminCommentService;
import com.fzy.weblog.common.domain.dos.CommentDO;
import com.fzy.weblog.common.domain.mapper.CommentMapper;
import com.fzy.weblog.common.enums.CommentStatusEnum;
import com.fzy.weblog.common.enums.ResponseCodeEnum;
import com.fzy.weblog.common.exception.BizException;
import com.fzy.weblog.common.utils.PageResponse;
import com.fzy.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@Slf4j
public class AdminCommentServiceImpl implements AdminCommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public Response findCommentPageList(FindCommentPageListReqVO findCommentPageListReqVO) {
        // 获取当前页、以及每页需要展示的数据数量
        Long current = findCommentPageListReqVO.getCurrent();
        Long size = findCommentPageListReqVO.getSize();
        LocalDate startDate = findCommentPageListReqVO.getStartDate();
        LocalDate endDate = findCommentPageListReqVO.getEndDate();
        String routerUrl = findCommentPageListReqVO.getRouterUrl();
        Integer status = findCommentPageListReqVO.getStatus();

        // 执行分页查询
        Page<CommentDO> commentDOPage = commentMapper.selectPageList(current, size, routerUrl, startDate, endDate, status);

        List<CommentDO> commentDOS = commentDOPage.getRecords();

        // DO 转 VO
        List<FindCommentPageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(commentDOS)) {
            vos = commentDOS.stream()
                    .map(commentDO -> {
                        FindCommentPageListRspVO vo = FindCommentPageListRspVO.builder()
                                .id(commentDO.getId())
                                .routerUrl(commentDO.getRouterUrl())
                                .avatar(commentDO.getAvatar())
                                .nickname(commentDO.getNickname())
                                .mail(commentDO.getMail())
                                .website(commentDO.getWebsite())
                                .createTime(commentDO.getCreateTime())
                                .content(commentDO.getContent())
                                .status(commentDO.getStatus())
                                .reason(commentDO.getReason())
                                .build();
                        return vo;
                    })
                    .collect(Collectors.toList());
        }

        return PageResponse.success(commentDOPage, vos);
    }
    /**
     * 删除评论
     *
     * @param deleteCommentReqVO
     * @return
     */
    @Override
    public Response deleteComment(DeleteCommentReqVO deleteCommentReqVO) {
        Long commentId = deleteCommentReqVO.getId();
        // 查询该评论是一级评论，还是二级评论
        CommentDO commentDO=commentMapper.selectById(commentId);
        if (Objects.isNull(commentDO)){
            log.warn("该评论不存在, commentId: {}", commentId);
            throw new BizException(ResponseCodeEnum.COMMENT_NOT_FOUND);
        }
        // 删除评论
        commentMapper.deleteById(commentId);
        Long replayCommentId = commentDO.getReplyCommentId();
        // 一级评论
       if (Objects.isNull(replayCommentId)){
commentMapper.deleteByParentCommentId(commentId);
       }else {
           // 删除此评论, 以及此评论下的所有回复
           deleteAllChildComment(commentId);
       }
        return Response.success();
    }

    @Override
    public Response examine(ExamineCommentReqVO examineCommentReqVO) {
        Long commentId = examineCommentReqVO.getId();
        Integer status = examineCommentReqVO.getStatus();
        String reason = examineCommentReqVO.getReason();
        // 根据提交的评论 ID 查询该条评论
        CommentDO commentDO = commentMapper.selectById(commentId);
        if (Objects.isNull(commentDO)){
            log.warn("该评论不存在, commentId: {}", commentId);
            throw new BizException(ResponseCodeEnum.COMMENT_NOT_FOUND);
        }
        Integer currentStatus = commentDO.getStatus();
        // 若未处于待审核状态
        if (!Objects.equals(currentStatus, CommentStatusEnum.WAIT_EXAMINE.getCode())) {
            log.warn("该评论未处于待审核状态, commentId: {}", commentId);
            throw new BizException(ResponseCodeEnum.COMMENT_STATUS_NOT_WAIT_EXAMINE);
        }
        commentMapper.updateById(commentDO.builder()

                .id(commentId)
                .status(status)
                .reason(reason)
                        .updateTime(LocalDateTime.now())
                .build()

        );
        // 发送文章发布事件
        eventPublisher.publishEvent(new UpdateCommentEvent(this, commentId));
        return Response.success();
    }

    /**
     * 递归删除所有子评论
     * @param commentId
     */
    private void deleteAllChildComment(Long commentId){
        List<CommentDO> childCommentDOS = commentMapper.selectByReplyCommentId(commentId);

        if (CollectionUtils.isEmpty(childCommentDOS))
            return;

        // 循环递归删除
        childCommentDOS.forEach(childCommentDO -> {
            Long childCommentId = childCommentDO.getId();

            commentMapper.deleteById(childCommentId);
            // 递归调用
            deleteAllChildComment(childCommentId);
        });

    }
}
