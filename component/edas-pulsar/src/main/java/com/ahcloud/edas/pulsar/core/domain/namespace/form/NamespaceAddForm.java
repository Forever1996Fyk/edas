package com.ahcloud.edas.pulsar.core.domain.namespace.form;

import com.ahcloud.edas.pulsar.core.domain.namespace.dto.PolicyDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 10:22
 **/
@Data
public class NamespaceAddForm {

    /**
     * 租户id
     */
    @NotNull(message = "租户id不能为空")
    private Long tenantId;

    /**
     * 命名空间名称
     */
    @NotEmpty(message = "命名空间名称不能为空")
    private String namespaceName;

    /**
     * 命名空间策略json
     */
    @Valid
    @NotNull(message = "命名空间策略不能为空")
    private PolicyDTO policyDTO;

    /**
     * 说明
     */
    private String description;
}
