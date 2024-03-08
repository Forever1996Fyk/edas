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
 * @create: 2024/2/26 17:25
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ConsumeProcessVO {

    /**
     * topicId
     */
    private Long topicId;

    /**
     * subscriptionId
     */
    private Long subscriptionId;

    /**
     * 分区id
     */
    private String partitionId;

    /**
     * topicName
     */
    private String topicName;

    /**
     * 消费速率
     */
    private String msgRateOut;

    /**
     * 消息消息大小
     */
    private String msgThroughPutOut;

    /**
     * 消息积压数
     */
    private Long backlog;
}
