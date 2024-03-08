package com.ahcloud.edas.powerjob.biz.domain.appinfo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 17:58
 **/
@Data
public class PowerJobAppInfoAddForm {

    /**
     * appId
     */
    @NotNull(message = "appId不能为空")
    private Long appId;

    /**
     * app编码
     */
    @NotEmpty(message = "appCode不能为空")
    private String appCode;

    /**
     * 环境变量
     */
    @NotEmpty(message = "环境变量不能为空")
    private String env;
}
