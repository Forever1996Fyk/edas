package com.ahcloud.edas.rocketmq.core.domain.subscribe.vo;

import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.SubscriptionGroupConfigDetail;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/5 15:37
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeVO {

    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * appId
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long appId;

    /**
     * app编码
     */
    private String appCode;

    /**
     * 环境变量
     */
    private String env;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * topicId
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long topicId;

    /**
     * broker列表
     */
    private List<String> brokerNameList;

    /**
     * TPS
     */
    private Integer consumerTps;

    /**
     * 延迟
     */
    private Long diffTotal;

    /**
     * 订阅组配置详情
     */
    private SubscriptionGroupConfigDetail groupConfigDetail;
}
