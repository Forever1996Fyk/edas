package com.ahcloud.edas.powerjob.biz.domain.instance.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/22 10:28
 **/
@Data
public class OperateInstanceForm {

    /**
     * powerJob appId
     */
    @NotNull(message = "powerJobAppId不能为空")
    private Long powerJobAppId;

    /**
     * 实例id
     */
    @NotNull(message = "实例id不能为空")
    private Long instanceId;
}
