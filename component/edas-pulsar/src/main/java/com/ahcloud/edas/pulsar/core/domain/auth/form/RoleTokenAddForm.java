package com.ahcloud.edas.pulsar.core.domain.auth.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/20 14:56
 **/
@Data
public class RoleTokenAddForm {

    /**
     * appId
     */
    @NotNull(message = "参数错误")
    private Long appId;

    /**
     * app编码
     */
    @NotEmpty(message = "参数错误")
    private String appCode;

    /**
     * env
     */
    @NotEmpty(message = "参数错误")
    private String env;
}
