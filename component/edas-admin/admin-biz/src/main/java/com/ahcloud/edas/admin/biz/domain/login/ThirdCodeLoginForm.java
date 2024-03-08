package com.ahcloud.edas.admin.biz.domain.login;

import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ThirdSourceEnum;
import com.ahcloud.edas.common.annotation.EnumValid;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description: 第三方登录
 * @author: YuKai Fan
 * @create: 2022-01-05 22:15
 **/
@Data
public class ThirdCodeLoginForm implements LoginForm {

    /**
     * 授权码
     */
    @NotEmpty(message = "授权码不能为空")
    private String loginTmpCode;

    /**
     * 第三方来源
     */
    @EnumValid(enumMethod = "isValid", enumClass = ThirdSourceEnum.class)
    private Integer source;
}
