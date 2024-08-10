package com.fzy.weblog.web.service.impl;

import com.fzy.weblog.common.domain.dos.BlogSettingsDO;
import com.fzy.weblog.common.domain.mapper.BlogSettingsMapper;
import com.fzy.weblog.common.utils.Response;
import com.fzy.weblog.web.model.vo.blogsettings.FindBlogSettingsDetailRspVO;
import com.fzy.weblog.web.service.BlogSettingsService;
import jdk.internal.dynalink.linker.LinkerServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogSettingsServiceImpl implements BlogSettingsService {
    @Autowired
    private BlogSettingsMapper blogSettingsMapper;

    /**
     * 获取博客设置信息
     *
     * @return
     */
    @Override
    public Response findDetail() {
        //查询博客信息
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        //DO转VO
FindBlogSettingsDetailRspVO vos=FindBlogSettingsDetailRspVO.builder()
        .author(blogSettingsDO.getAuthor())
        .avatar(blogSettingsDO.getAvatar())
        .csdnHomepage(blogSettingsDO.getCsdnHomepage())
        .giteeHomepage(blogSettingsDO.getGiteeHomepage())
        .githubHomepage(blogSettingsDO.getGithubHomepage())
        .introduction(blogSettingsDO.getIntroduction())
        .logo(blogSettingsDO.getLogo())
        .name(blogSettingsDO.getName())
        .zhihuHomepage(blogSettingsDO.getZhihuHomepage())
        .build();
        return Response.success(vos);
    }
}
