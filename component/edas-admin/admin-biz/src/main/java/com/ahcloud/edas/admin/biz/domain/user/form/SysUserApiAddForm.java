package com.ahcloud.edas.admin.biz.domain.user.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: permissions-center
 * @description: 设置用户角色form
 * @author: YuKai Fan
 * @create: 2022-03-21 22:11
 **/
@Data
public class SysUserApiAddForm {

    /**
     * 用户昵称
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * apiCode列表
     */
    private List<String> apiCodeList;
}
