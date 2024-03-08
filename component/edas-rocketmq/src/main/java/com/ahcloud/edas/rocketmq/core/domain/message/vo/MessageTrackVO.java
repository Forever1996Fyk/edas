package com.ahcloud.edas.rocketmq.core.domain.message.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/14 16:34
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageTrackVO {

    /**
     * 消费者组
     */
    private String consumerGroup;

    /**
     * 异常原因
     */
    private String exceptionDesc;

    /**
     * 轨迹类型
     */
    private Integer trackType;

    /**
     * 轨迹类型名称
     */
    private String trackTypeName;
}
