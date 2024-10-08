package com.fzy.weblog.admin.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fzy.weblog.admin.model.vo.tag.AddTagReqVO;
import com.fzy.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.fzy.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.fzy.weblog.admin.model.vo.tag.SearchTagReqVO;
import com.fzy.weblog.admin.service.AdminCategoryService;
import com.fzy.weblog.admin.service.AdminTagService;
import com.fzy.weblog.common.aspect.ApiOperationLog;
import com.fzy.weblog.common.domain.dos.TagDO;
import com.fzy.weblog.common.domain.mapper.TagMapper;
import com.fzy.weblog.common.utils.PageResponse;
import com.fzy.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import io.swagger.models.auth.In;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 标签模块")
public class AdminTagController {
    @Autowired
    private AdminCategoryService adminCategoryService;
    @Autowired
    private AdminTagService adminTagService;
    @Autowired
    private TagMapper tagMapper;

    @PostMapping("/tag/add")
    @ApiOperation(value = "添加标签")
    @ApiOperationLog(description = "添加标签")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response addTag(@RequestBody @Validated AddTagReqVO addTagReqVO) {
        return adminTagService.addTags(addTagReqVO);

    }

    /**
     * 标签分页数据获取
     *
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

    /**
     * 导出数据
     */
//    @GetMapping("/tag/export")
//    public void exportTag(HttpServletResponse response) throws IOException {
//        ExcelWriter writer = ExcelUtil.getWriter(true);
//        Response tagSelectList = adminTagService.findTagSelectList();
//        writer.write((List<TagDO>) tagSelectList.getData(),true);
//        ServletOutputStream outputStream = response.getOutputStream();
//        writer.flush(outputStream,true);
//        writer.close();
//    }


    //导入接口
    @PostMapping("/tag/import")
    public Response importTag(MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<TagDO> tagDOS = reader.readAll(TagDO.class);
        try{
            adminTagService.saveBatch(tagDOS);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Response.fail("导入失败");
        }
        return Response.success("导入成功");
    }


    //导出接口

    @GetMapping("/tag/export")
    public void exportTag(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String ids,
            HttpServletResponse response) throws IOException {

        // 创建 ExcelWriter
        ExcelWriter writer = ExcelUtil.getWriter(true);
        QueryWrapper<TagDO> queryWrapper = new QueryWrapper<>();
        List<TagDO> tags;

        // 从数据库中查询标签数据
        if (StringUtils.isNotBlank(ids)) {
            List<Integer> idsArr = Arrays.stream(ids.split(","))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            queryWrapper.in("id", idsArr);
        } else {
            queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        }

        // 查询标签
        tags = tagMapper.selectList(queryWrapper);
        System.out.println("查询到的标签数量: " + tags.size());

        // 打印每个标签的信息以便调试
        tags.forEach(tag -> System.out.println("标签信息: " + tag));

        if (tags.isEmpty()) {
            System.out.println("未查询到任何数据");
        } else {
            System.out.println("数据已写入 Excel");
        }

        // 自定义标题别名
        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("name", "名称");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("updateTime", "更新时间");
        writer.addHeaderAlias("isDeleted", "是否删除");

        // 一次写到 excel，使用默认方式强制输出标题
        writer.write(tags, true);

        // 设置日期格式
        Sheet sheet = writer.getSheet();
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        DataFormat format = writer.getWorkbook().createDataFormat();
        cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));

        // 获取最后一行并应用样式
        int rowIndex = sheet.getLastRowNum();
        for (int i = 1; i <= rowIndex; i++) { // 从1开始，跳过标题行
            Cell createTimeCell = sheet.getRow(i).getCell(2); // 假设创建时间在第三列（索引为2）
            Cell updateTimeCell = sheet.getRow(i).getCell(3); // 假设更新时间在第四列（索引为3）

            if (createTimeCell != null && createTimeCell.getCellType() == CellType.NUMERIC) {
                createTimeCell.setCellStyle(cellStyle);
            }

            if (updateTimeCell != null && updateTimeCell.getCellType() == CellType.NUMERIC) {
                updateTimeCell.setCellStyle(cellStyle);
            }
        }

        // 设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("标签数据", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        // 将 writer 中的数据刷新到流中
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            writer.flush(outputStream, true);
            System.out.println("文件已成功输出到浏览器");
        } catch (Exception e) {
            System.out.println("输出流发生错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭 writer
            writer.close();
            System.out.println("Excel Writer 已关闭");
        }
    }
}

