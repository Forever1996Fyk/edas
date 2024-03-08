package com.ahcloud.edas.admin.biz.domain.user.app;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/27 11:42
 **/
@Data
public class UpdateUserPasswordForm {

    /**
     * 新密码
     */
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
}
