package com.ahcloud.edas.admin.biz.domain.user.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-21 14:10
 **/
@Data
public class SysUserInfoUpdateForm {

    /**
     * 用户昵称
     */
    @NotEmpty(message = "昵称不能为空")
    @Size(min = 3, max = 10, message = "长度最大为10个字, 最少为3个字")
    private String nickName;

    /**
     * 用户性别
     */
    private Integer sex;
}
