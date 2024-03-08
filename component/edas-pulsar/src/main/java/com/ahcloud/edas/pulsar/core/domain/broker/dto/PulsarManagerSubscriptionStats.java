package com.ahcloud.edas.pulsar.core.domain.broker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/3/5 09:23
 **/
@Getter
@Setter
@NoArgsConstructor
public class PulsarManagerSubscriptionStats {

    private long numberOfEntriesSinceFirstNotAckedMessage;
    private long totalNonContiguousDeletedMessagesRange;

    /**
     * Total rate of messages delivered on this subscription. msg/s
     */
    private double msgRateOut;

    /**
     * Total throughput delivered on this subscription. bytes/s
     */
    private double msgThroughputOut;

    /**
     * Total rate of messages redelivered on this subscription. msg/s
     */
    private double msgRateRedeliver;

    /**
     * Number of messages in the subscription backlog
     */
    private long msgBacklog;

    /**
     * Whether this subscription is Exclusive or Shared or Failover
     */
    private String type;

    /**
     * Total rate of messages expired on this subscription. msg/s
     */
    private double msgRateExpired;

    /**
     * List of connected consumers on this subscription w/ their stats
     */
    private List<PulsarManagerConsumerStats> consumers;

    /**
     * Mark that the subscription state is kept in sync across different regions
     */
    private boolean isReplicated;

}
