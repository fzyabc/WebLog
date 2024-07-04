package com.fzy.weblog.admin.model.vo.tag;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: Fantazy
 * @description: 描述
 * @date: 2020/11/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "删除标签VO")
public class DeleteTagReqVO {
    @NotNull(message = "标签ID不能为空")
    private Long id;
}
