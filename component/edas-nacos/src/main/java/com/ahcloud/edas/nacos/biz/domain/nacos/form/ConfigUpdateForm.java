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
public class ConfigUpdateForm {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;

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
