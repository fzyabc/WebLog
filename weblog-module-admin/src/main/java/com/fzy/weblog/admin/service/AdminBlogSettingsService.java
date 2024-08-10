package com.fzy.weblog.admin.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzy.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.fzy.weblog.common.domain.dos.BlogSettingsDO;
import com.fzy.weblog.common.utils.Response;

public interface AdminBlogSettingsService  {
    Response updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);
    Response findDetail();
}
