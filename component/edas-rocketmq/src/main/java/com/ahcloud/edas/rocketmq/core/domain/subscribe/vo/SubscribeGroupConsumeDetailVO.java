package com.ahcloud.edas.rocketmq.core.domain.subscribe.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/11 11:38
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeGroupConsumeDetailVO {

    /**
     * 订阅组名称
     */
    private String groupName;

    /**
     * 延迟
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long diffTotal;

    /**
     * 最后消费时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime lastConsumeTime;

    /**
     * 队列状态信息列表
     */
    private List<BrokerQueueStateInfo>  queueStateInfoList;
}
