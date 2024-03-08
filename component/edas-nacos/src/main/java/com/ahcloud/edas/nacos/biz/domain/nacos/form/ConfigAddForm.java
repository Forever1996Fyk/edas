package com.ahcloud.edas.nacos.biz.domain.nacos.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/7 00:02
 **/
@Data
public class ConfigAddForm {

    /**
     * appId
     */
    @NotNull(message = "appId不能为空")
    private Long appId;

    /**
     * app编码
     */
    @NotEmpty(message = "app编码不能为空")
    private String appCode;

    /**
     * 环境变量
     */
    @NotEmpty(message = "环境变量不能为空")
    private String env;

    /**
     * 命名空间
     */
    @NotEmpty(message = "命名空间不能为空")
    private String tenant;

    /**
     * 配置id
     */
    @NotEmpty(message = "配置id不能为空")
    private String dataId;

    /**
     * 配置分组
     */
    @NotEmpty(message = "配置分组不能为空")
    private String bizGroup;

    /**
     * 内容
     */
    @NotEmpty(message = "配置内容不能为空")
    private String content;

    /**
     * 配置类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;
}
