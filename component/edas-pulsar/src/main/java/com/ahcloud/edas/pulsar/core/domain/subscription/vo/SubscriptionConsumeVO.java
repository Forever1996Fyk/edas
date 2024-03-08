package com.ahcloud.edas.pulsar.core.domain.subscription.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/23 14:54
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionConsumeVO {

    /**
     * topicId
     */
    private Long topicId;

    /**
     * subscriptionId
     */
    private Long subscriptionId;

    /**
     * 消费者名称
     */
    private String consumeName;

    /**
     * 客户端地址
     */
    private String clientAddress;

    /**
     * 客户端版本
     */
    private String clientVersion;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date startTime;
}
