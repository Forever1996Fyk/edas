package com.ahcloud.edas.pulsar.core.domain.broker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/3/5 09:22
 **/
@Getter
@Setter
@NoArgsConstructor
public class PulsarManagerTopicStats {

    /**
     * Total rate of messages published on the topic. msg/s
     */
    private double msgRateIn;

    /**
     * Total throughput of messages published on the topic. byte/s
     */
    private double msgThroughputIn;

    /**
     * Total rate of messages dispatched for the topic. msg/s
     */
    private double msgRateOut;

    /**
     * Total throughput of messages dispatched for the topic. byte/s
     */
    private double msgThroughputOut;

    /**
     * Average size of published messages. bytes
     */
    private double averageMsgSize;

    /**
     * Space used to store the messages for the topic. bytes
     */
    private long storageSize;

    private int pendingAddEntriesCount;

    /**
     * List of connected publishers on this topic w/ their stats
     */
    private ArrayList<PulsarManagerPublisherStats> publishers;

    /**
     * Map of subscriptions with their individual statistics
     */
    private HashMap<String, PulsarManagerSubscriptionStats> subscriptions;

    /**
     * Map of replication statistics by remote cluster context
     */
    private HashMap<String, PulsarManagerReplicatorStats> replication;

    private String deduplicationStatus;

    /**
     * 发布到topic的总字节数
     */
    private Long bytesInCount;

    /**
     * topic接收的总字节数
     */
    private Long bytesOutCount;

    /**
     * 发布到topic的总消息数
     */
    private Long msgInCount;

    /**
     * 传递给消费者的消息总数
     */
    private Long msgOutCount;

}
