package com.fzy.weblog.admin.model.vo.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: FZY
 * @create: 2021-07-08 16:01
 * @description:标签分页
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindTagPageListRspVO {
    /**
     * 标签id
     */
    private Long id;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
