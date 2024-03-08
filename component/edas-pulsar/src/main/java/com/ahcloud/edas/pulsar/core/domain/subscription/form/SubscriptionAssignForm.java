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
public class SubscriptionAssignForm {

    /**
     * topicId
     */
    @NotNull(message = "参数错误")
    private Long topicId;

    /**
     * appId
     */
    @NotNull(message = "参数错误")
    private Long appId;
}
