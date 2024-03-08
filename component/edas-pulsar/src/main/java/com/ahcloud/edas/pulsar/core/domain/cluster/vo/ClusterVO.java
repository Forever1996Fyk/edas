package com.ahcloud.edas.pulsar.core.domain.cluster.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/27 15:18
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClusterVO {

    /**
     * 集群名称
     */
    private String cluster;

    /**
     * broker数
     */
    private Integer brokersNum;

    /**
     * serviceUrl
     */
    private String serviceUrl;

    /**
     * serviceUrlTls
     */
    private String serviceUrlTls;

    /**
     * brokerServiceUrl
     */
    private String brokerServiceUrl;

    /**
     * brokerServiceUrlTls
     */
    private String brokerServiceUrlTls;
}
