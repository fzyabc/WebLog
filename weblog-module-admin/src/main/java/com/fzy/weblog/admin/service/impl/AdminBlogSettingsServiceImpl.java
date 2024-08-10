package com.fzy.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzy.weblog.admin.model.vo.blogsettings.FindBlogSettingsRspVO;
import com.fzy.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.fzy.weblog.admin.service.AdminBlogSettingsService;
import com.fzy.weblog.common.domain.dos.BlogSettingsDO;
import com.fzy.weblog.common.domain.mapper.BlogSettingsMapper;
import com.fzy.weblog.common.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AdminBlogSettingsServiceImpl extends ServiceImpl<BlogSettingsMapper, BlogSettingsDO> implements AdminBlogSettingsService {

    private final BlogSettingsMapper blogSettingsMapper;

    public AdminBlogSettingsServiceImpl(BlogSettingsMapper blogSettingsMapper) {
        this.blogSettingsMapper = blogSettingsMapper;
    }

    @Override
    public Response updateBlogSettings(@RequestBody @Validated UpdateBlogSettingsReqVO updateBlogSettingsReqVO) {
        BlogSettingsDO blogSettingsDO = BlogSettingsDO.builder()
                .id(1L)
                .logo(updateBlogSettingsReqVO.getLogo())
                .name(updateBlogSettingsReqVO.getName())
                .author(updateBlogSettingsReqVO.getAuthor())
                .introduction(updateBlogSettingsReqVO.getIntroduction())
                .avatar(updateBlogSettingsReqVO.getAvatar())
                .githubHomepage(updateBlogSettingsReqVO.getGithubHomepage())
                .giteeHomepage(updateBlogSettingsReqVO.getGiteeHomepage())
                .csdnHomepage(updateBlogSettingsReqVO.getCsdnHomepage())
                .zhihuHomepage(updateBlogSettingsReqVO.getZhihuHomepage())
                .build();
saveOrUpdate(blogSettingsDO);
        return Response.success();
    }

    @Override
    public Response findDetail() {
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        //DO转VO
        FindBlogSettingsRspVO findBlogSettingsRspVO = FindBlogSettingsRspVO.builder()
                .logo(blogSettingsDO.getLogo())
                .name(blogSettingsDO.getName())
                .author(blogSettingsDO.getAuthor())
                .introduction(blogSettingsDO.getIntroduction())
                .avatar(blogSettingsDO.getAvatar())
                .githubHomepage(blogSettingsDO.getGithubHomepage())
                .giteeHomepage(blogSettingsDO.getGiteeHomepage())
                .csdnHomepage(blogSettingsDO.getCsdnHomepage())
                .zhihuHomepage(blogSettingsDO.getZhihuHomepage())
                .build();
        return Response.success(findBlogSettingsRspVO);
    }
}
