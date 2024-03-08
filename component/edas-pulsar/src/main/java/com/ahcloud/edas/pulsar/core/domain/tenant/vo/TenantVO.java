package com.ahcloud.edas.pulsar.core.domain.tenant.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/18 17:10
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TenantVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 角色集合
     */
    private String adminRoles;

    /**
     * 集群集合
     */
    private String allowedClusters;
}
