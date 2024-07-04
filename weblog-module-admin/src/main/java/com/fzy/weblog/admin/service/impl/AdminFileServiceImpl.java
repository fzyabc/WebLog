package com.fzy.weblog.admin.service.impl;

import com.fzy.weblog.admin.model.vo.file.UploadFileRspVO;
import com.fzy.weblog.admin.service.AdminFileService;
import com.fzy.weblog.admin.utils.MinioUtil;
import com.fzy.weblog.common.enums.ResponseCodeEnum;
import com.fzy.weblog.common.exception.BizException;
import com.fzy.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
@Slf4j
public class AdminFileServiceImpl implements AdminFileService {
    @Autowired
private MinioUtil minioUtil;
    @Override
    public Response uploadFile(MultipartFile file) {
        //上传文件
        try {
            String url = minioUtil.uploadFile(file);
            // 构建成功返参，将图片的访问链接返回
            return Response.success(UploadFileRspVO.builder().url(url).build());
        } catch (Exception e) {
            log.error("==> 上传文件至 Minio 错误: ", e);
            // 手动抛出业务异常，提示 “文件上传失败”
            throw new BizException(ResponseCodeEnum.FILE_UPLOAD_FAILED);
        }


    }
}
