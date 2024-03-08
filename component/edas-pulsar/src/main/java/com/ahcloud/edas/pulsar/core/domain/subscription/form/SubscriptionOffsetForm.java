package com.ahcloud.edas.pulsar.core.domain.subscription.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/23 16:16
 **/
@Data
public class SubscriptionOffsetForm {

    /**
     * 设置时间
     */
    @NotNull(message = "设置时间不能为空")
    private Date offsetTime;

    /**
     * 订阅id
     */
    @NotNull(message = "参数错误")
    private Long subscriptionId;
}
