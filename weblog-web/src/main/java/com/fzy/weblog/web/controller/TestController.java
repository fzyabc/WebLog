package com.fzy.weblog.web.controller;

import com.fzy.weblog.common.utils.JsonUtil;
import com.fzy.weblog.web.model.User;
import com.fzy.weblog.common.aspect.ApiOperationLog;
import com.fzy.weblog.common.enums.ResponseCodeEnum;
import com.fzy.weblog.common.exception.BizException;
import com.fzy.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@Slf4j
@Api(tags = "接口模块")
public class TestController {

    @PostMapping("/admin/test")
    @ApiOperationLog(description = "测试接口")
    @ApiOperation("测试接口")
    public Response test(@RequestBody @Validated User user) {
log.info(JsonUtil.toJsonString(user));
user.setCreateTime(LocalDateTime.now());
user.setUpdateDate(LocalDate.now());
user.setTime(LocalTime.now());
        return Response.success(user);
    }
    @PostMapping("/admin/update")
    @ApiOperationLog(description = "测试更新接口")
    @ApiOperation(value = "测试更新接口")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response testUpdate() {
        return Response.success();
    }
}