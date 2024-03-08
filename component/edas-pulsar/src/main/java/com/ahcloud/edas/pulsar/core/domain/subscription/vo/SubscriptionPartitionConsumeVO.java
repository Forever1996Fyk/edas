package com.ahcloud.edas.pulsar.core.domain.subscription.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/23 16:38
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPartitionConsumeVO {

    /**
     * 分区id
     */
    private String partitionId;

    /**
     * 消费速率
     */
    private String msgRateOut;

    /**
     * 消费带宽
     */
    private String msgThroughputOut;
}
