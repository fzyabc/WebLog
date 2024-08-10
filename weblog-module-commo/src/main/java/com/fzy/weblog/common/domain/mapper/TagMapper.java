package com.fzy.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzy.weblog.common.domain.dos.CategoryDO;
import com.fzy.weblog.common.domain.dos.TagDO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public interface TagMapper extends BaseMapper<TagDO> {
default Page<TagDO> selectPageList(Long current, Long size, String name, LocalDate startDate, LocalDate endDate) {
    Page<TagDO> page = new Page<>(current, size);
    //构建查询条件
    LambdaQueryWrapper<TagDO> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.like(Objects.nonNull(name), TagDO::getName, name)
            .ge(Objects.nonNull(startDate), TagDO::getCreateTime, startDate)
            .le(Objects.nonNull(endDate), TagDO::getCreateTime, endDate)
            .orderByDesc(TagDO::getCreateTime)  ;
  return selectPage(page, queryWrapper);
}

    /**
     * 根据标签关键字模糊查询
     * @param key
     * @return
     */
    default List<TagDO> selectByKey(String key) {
LambdaQueryWrapper<TagDO> queryWrapper = new LambdaQueryWrapper<>();
queryWrapper.like(TagDO::getName, key).orderByDesc(TagDO::getCreateTime);
return selectList(queryWrapper);

}
    /**
     * 根据标签 ID 批量查询
     * @param tagIds
     * @return
     */
    default List<TagDO> selectByIds(List<Long> tagIds) {
        return selectList(Wrappers.<TagDO>lambdaQuery()
                .in(TagDO::getId, tagIds));
    }
}
