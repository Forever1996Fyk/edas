package com.ahcloud.edas.pulsar.core.domain.subscription.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/20 10:47
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionVO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * appId
     */
    private Long appId;

    /**
     * app编码
     */
    private String appCode;

    /**
     * env
     */
    private String env;

    /**
     * topicId
     */
    private Long topicId;

    /**
     * topic名称
     */
    private String topicName;

    /**
     * 订阅名称
     */
    private String subscriptionName;

    /**
     * 说明
     */
    private String description;

    /**
     * 消息积压数
     */
    private Long backlog;

    /**
     * 订阅模式
     */
    private String subscriptionType;
}
