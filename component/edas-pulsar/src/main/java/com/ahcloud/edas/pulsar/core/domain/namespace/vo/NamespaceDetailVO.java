package com.ahcloud.edas.pulsar.core.domain.namespace.vo;

import com.ahcloud.edas.pulsar.core.domain.common.StatsDTO;
import com.ahcloud.edas.pulsar.core.domain.namespace.dto.PolicyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 10:35
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NamespaceDetailVO {

    /**
     * id
     */
    private Long id;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 命名空间名称
     */
    private String namespaceName;

    /**
     * 命名空间策略json
     */
    private PolicyDTO policyDTO;

    /**
     * 说明
     */
    private String description;

    /**
     * 统计状态
     */
    private StatsDTO statsDTO;
}
