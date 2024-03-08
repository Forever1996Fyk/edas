package com.ahcloud.edas.rocketmq.core.domain.subscribe.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/10 15:26
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionGroupConfigDetail {

    /**
     * 订阅组名称
     */
    @NotEmpty(message = "订阅组名称不能为空")
    @Pattern(regexp = "^[%|a-zA-Z0-9_-]+$", message = "订阅组名称格式错误")
    private String groupName;

    /**
     * 是否开启消费
     */
    private boolean consumeEnable = true;

    /**
     * 最小消费
     */
    private boolean consumeFromMinEnable = true;

    /**
     * 是否开启广播消费
     */
    private boolean consumeBroadcastEnable = true;

    /**
     * 是否开始顺序消费
     */
    private boolean consumeMessageOrderly = false;

    /**
     * 重试队列数
     */
    private int retryQueueNums = 1;

    /**
     * 重试最大次数
     */
    private int retryMaxTimes = 16;

    /**
     * brokerId
     */
    private long brokerId = 0L;

    /**
     * 当消费过慢时切换broker
     */
    private long whichBrokerWhenConsumeSlowly = 1L;

    /**
     * 是否可通知消费改变
     */
    private boolean notifyConsumerIdsChangedEnable = true;

    /**
     * 消费超时时间
     */
    private int consumeTimeoutMinute = 15;

    /**
     * 其他属性参数
     */
    private Map<String, String> attributes = new HashMap();
}
