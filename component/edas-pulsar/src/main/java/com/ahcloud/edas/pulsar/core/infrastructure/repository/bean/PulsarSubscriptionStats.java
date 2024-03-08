package com.ahcloud.edas.pulsar.core.infrastructure.repository.bean;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * pulsar订阅状态统计
 * </p>
 *
 * @author auto_generation
 * @since 2024-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pulsar_subscription_stats")
public class PulsarSubscriptionStats implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 命名空间id
     */
    private Long namespaceId;

    /**
     * 命名空间名称
     */
    private String namespaceName;

    /**
     * topicId
     */
    private Long topicId;

    /**
     * subscriptionId
     */
    private Long subscriptionId;

    /**
     * subscription名称
     */
    private String subscriptionName;

    /**
     * 消息消费速率
     */
    private BigDecimal msgRateOut;

    /**
     * 消息消费大小
     */
    private BigDecimal msgThroughputOut;

    /**
     * 消息重新投递率
     */
    private BigDecimal msgRateRedeliver;

    /**
     * 消息过期率
     */
    private BigDecimal msgRateExpired;

    /**
     * 消息平均大小
     */
    private BigDecimal averageMsgSize;

    /**
     * 存储大小
     */
    private Long storageSize;

    /**
     * 统计时间戳
     */
    private Long timeStamp;


}
