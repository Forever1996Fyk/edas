package com.ahcloud.edas.pulsar.core.domain.topic.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/22 17:22
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TopicProduceVO {

    /**
     * 生产者id
     */
    private Long produceId;

    /**
     * 生产者名称
     */
    private String produceName;

    /**
     * 生产者地址
     */
    private String produceAddress;

    /**
     * 消息生产速率
     */
    private String msgRateIn;

    /**
     * 消息生产吞吐量
     */
    private String msgThroughputIn;

    /**
     * 平均消息大小
     */
    private String averageMsgSize;

    /**
     * 客户端版本
     */
    private String clientVersion;
}
