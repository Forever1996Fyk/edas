package com.ahcloud.edas.rocketmq.core.domain.acl.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/2 11:20
 **/
@Data
public class RmqAppConfigAddForm {

    /**
     * appId
     */
    @NotNull(message = "参数错误")
    private Long appId;

    /**
     * appCode
     */
    @NotEmpty(message = "参数错误")
    private String appCode;

    /**
     * env
     */
    @NotEmpty(message = "参数错误")
    private String env;
}
