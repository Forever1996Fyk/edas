package com.ahcloud.edas.pulsar.core.domain.tenant.form;

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
public class TenantUpdateForm {

    /**
     * id
     */
    @NotNull(message = "参数错误")
    private Long id;

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
}
