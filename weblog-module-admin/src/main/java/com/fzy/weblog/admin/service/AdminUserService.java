package com.fzy.weblog.admin.service;

import com.fzy.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.fzy.weblog.common.utils.Response;


public interface AdminUserService {
    Response updatePassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO);
    Response findUserInfo();
}
