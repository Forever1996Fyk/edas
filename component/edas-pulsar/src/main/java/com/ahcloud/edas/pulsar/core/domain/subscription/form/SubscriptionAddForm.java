package com.ahcloud.edas.pulsar.core.domain.subscription.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/20 10:41
 **/
@Data
public class SubscriptionAddForm {

    /**
     * appId
     */
    @NotNull(message = "参数错误")
    private Long appId;

    /**
     * app编码
     */
    @NotEmpty(message = "参数错误")
    private String appCode;

    /**
     * env
     */
    @NotEmpty(message = "参数错误")
    private String env;

    /**
     * topicId
     */
    @NotNull(message = "topic参数错误")
    private Long topicId;

    /**
     * 订阅名称
     */
    @NotEmpty(message = "订阅名称不能为空")
    private String subscriptionName;

    /**
     * 说明
     */
    private String description;
}
