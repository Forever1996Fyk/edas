package com.ahcloud.edas.pulsar.core.domain.topic.vo;

import com.ahcloud.edas.pulsar.core.domain.common.StatsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/26 16:51
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TopicDetailVO {

    /**
     * topicId
     */
    private Long topicId;

    /**
     * 环境变量
     */
    private String env;

    /**
     * 分区数
     */
    private Integer partitions;

    /**
     * 是否持久化
     */
    private Integer persistent;

    /**
     * 生产者数
     */
    private Integer producers;

    /**
     * 订阅数
     */
    private Integer subscriptions;

    /**
     * 统计状态
     */
    private StatsDTO statsDTO;
}
