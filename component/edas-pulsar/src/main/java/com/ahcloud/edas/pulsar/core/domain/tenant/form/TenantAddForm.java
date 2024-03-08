package com.ahcloud.edas.pulsar.core.domain.tenant.form;

import com.ahcloud.edas.common.annotation.EnumValid;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.enums.TenantTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/18 17:01
 **/
@Data
public class TenantAddForm {

    /**
     * 租户名称
     */
    @NotEmpty(message = "租户名称不能为空")
    private String tenantName;

    /**
     * 角色集合
     */
    @Size(min = 1, max = 5, message = "角色集合最大为5个最少为1个")
    private Set<String> adminRoles;

    /**
     * 集群
     */
    @Size(min = 1, message = "集群最少为1个")
    private Set<String> allowedClusters;

    /**
     * 租户类型
     */
    @EnumValid(enumClass = TenantTypeEnum.class, enumMethod = "isValid")
    private Integer type;
}
