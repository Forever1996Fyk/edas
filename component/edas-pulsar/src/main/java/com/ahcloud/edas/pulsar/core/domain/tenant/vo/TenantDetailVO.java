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
public class TenantDetailVO {

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
    private Set<String> adminRoles;

    /**
     * 集群集合
     */
    private Set<String> allowedClusters;

    /**
     * 命名空间数量
     */
    private Integer namespaceNum;

    /**
     * 消息接收速率
     */
    private String msgRateIn;

    /**
     * 消息消费速率
     */
    private String msgRateOut;

    /**
     * 消息接收大小
     */
    private String msgThroughPutIn;

    /**
     * 消息消息大小
     */
    private String msgThroughPutOut;

    /**
     * 存储大小
     */
    private Long storageSize;
}
