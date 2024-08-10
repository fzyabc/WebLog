package com.fzy.weblog.admin.controller;

import com.fzy.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.fzy.weblog.admin.model.vo.tag.AddTagReqVO;
import com.fzy.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.fzy.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.fzy.weblog.admin.model.vo.tag.SearchTagReqVO;
import com.fzy.weblog.admin.service.AdminCategoryService;
import com.fzy.weblog.admin.service.AdminTagService;
import com.fzy.weblog.common.aspect.ApiOperationLog;
import com.fzy.weblog.common.utils.PageResponse;
import com.fzy.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 标签模块")
public class AdminTagController {
    @Autowired
    private AdminCategoryService adminCategoryService;
    @Autowired
    private AdminTagService adminTagService;
    @PostMapping("/tag/add")
    @ApiOperation(value = "添加标签")
    @ApiOperationLog(description = "添加标签")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response addTag(@RequestBody @Validated AddTagReqVO addTagReqVO) {
        return adminTagService.addTags(addTagReqVO);

    }

    /**
     * 标签分页数据获取
     * @param findTagPageListReqVO
     * @return
     */
    @PostMapping("/tag/list")
    @ApiOperation(value = "标签分页数据获取")
    @ApiOperationLog(description = "标签分页数据获取")
    public PageResponse findCategoryList(@RequestBody @Validated FindTagPageListReqVO findTagPageListReqVO) {
        return adminTagService.findTagPageList(findTagPageListReqVO);
    }
    @PostMapping("/tag/delete")
    @ApiOperation(value = "删除标签")
    @ApiOperationLog(description = "删除标签")
    public Response deleteTag(@RequestBody @Validated DeleteTagReqVO deleteTagReqVO) {
        return adminTagService.deleteTag(deleteTagReqVO);

    }
//    @PostMapping("/tag/list")
//    @ApiOperation(value = "标签模糊查询")
//    @ApiOperationLog(description = "标签模糊查询")
//    public Response findCategorySelectList(@RequestBody @Validated FindTagPageListReqVO findTagPageListReqVO) {
//        return adminTagService.findTagSelectList();
//
//    }
    @PostMapping("/tag/search")
    @ApiOperation(value = "标签模糊查询")
    @ApiOperationLog(description = "标签模糊查询")
    public Response findCategorySelectList(@RequestBody @Validated SearchTagReqVO searchTagReqVO) {
        return adminTagService.searchTag(searchTagReqVO);

    }

    @PostMapping("/tag/select/list")
    @ApiOperation(value = "查询标签 Select 列表数据")
    @ApiOperationLog(description = "查询标签 Select 列表数据")
    public Response findTagSelectList() {
        return adminTagService.findTagSelectList();
    }

}
