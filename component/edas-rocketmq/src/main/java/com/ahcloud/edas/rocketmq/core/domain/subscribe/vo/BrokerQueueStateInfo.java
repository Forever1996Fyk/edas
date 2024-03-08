package com.ahcloud.edas.rocketmq.core.domain.subscribe.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/11 15:29
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrokerQueueStateInfo {

    /**
     * broker名称
     */
    private String brokerName;

    /**
     * 队列id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer queueId;

    /**
     * 代理者位点
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long brokerOffset;

    /**
     * 消费者点位
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long consumerOffset;

    /**
     * 堆积消息数量
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long piledCount;

    /**
     * 最后消费时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime lastConsumeTime;

    public Long getPiledCount() {
        Long brokerOffset = Optional.ofNullable(this.brokerOffset).orElse(0L);
        Long consumerOffset = Optional.ofNullable(this.consumerOffset).orElse(0L);
        return brokerOffset - consumerOffset;
    }
}
