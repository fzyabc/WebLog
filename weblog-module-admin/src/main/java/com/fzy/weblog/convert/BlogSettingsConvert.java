package com.fzy.weblog.convert;

import com.fzy.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.fzy.weblog.common.domain.dos.BlogSettingsDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface BlogSettingsConvert {
    /**
     * 初始化 convert 实例
     */
    BlogSettingsConvert INSTANCE =Mappers.getMapper(BlogSettingsConvert.class);

    /**
     * 将 VO 转化为 DO
     * @param bean
     * @return
     */
    BlogSettingsDO convertVO2DO(UpdateBlogSettingsReqVO bean);
}
