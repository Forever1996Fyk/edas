package com.ahcloud.edas.pulsar.core.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/26 16:57
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsDTO {

    /**
     * 消息接收速率
     */
    private String msgRateIn;

    /**
     * 消息消费速率
     */
    private String msgRateOut;

    /**
     * 消息接收大小
     */
    private String msgThroughPutIn;

    /**
     * 消息消息大小
     */
    private String msgThroughPutOut;

    /**
     * 存储大小
     */
    private Long storageSize;

    /**
     * 发布到topic的总字节数
     */
    private Long bytesInCounter;

    /**
     * topic接收的总字节数
     */
    private Long bytesOutCounter;

    /**
     * 发布到topic的总消息数
     */
    private Long msgInCounter;

    /**
     * 传递给消费者的消息总数
     */
    private Long msgOutCounter;
}
