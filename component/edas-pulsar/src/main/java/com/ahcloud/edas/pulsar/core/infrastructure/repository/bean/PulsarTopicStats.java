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
 * pulsarTopic状态统计
 * </p>
 *
 * @author auto_generation
 * @since 2024-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pulsar_topic_stats")
public class PulsarTopicStats implements Serializable {

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
     * topic名称
     */
    private String topicName;

    /**
     * 绑定
     */
    private String bundle;

    /**
     * 持久化
     */
    private String persistent;

    /**
     * 消息接收速率
     */
    private BigDecimal msgRateIn;

    /**
     * 消息消费速率
     */
    private BigDecimal msgRateOut;

    /**
     * 消息接收大小
     */
    private BigDecimal msgThroughputIn;

    /**
     * 消息消费大小
     */
    private BigDecimal msgThroughputOut;

    /**
     * 消息平均大小
     */
    private BigDecimal averageMsgSize;

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

    /**
     * 统计时间戳
     */
    private Long timeStamp;


}
