package com.ahcloud.edas.admin.biz.domain.code.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 23:03
 **/
@Data
public class LoginCodeForm {


    /**
     * 发送账号
     */
    @NotEmpty(message = "发送账号不能为空")
    private String sender;
}
