package com.ahcloud.edas.pulsar.core.domain.broker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/3/5 09:23
 **/
@Getter
@Setter
@NoArgsConstructor
public class PulsarManagerReplicatorStats {
    /**
     * Total rate of messages received from the remote cluster. msg/s
     */
    private double msgRateIn;

    /**
     * Total throughput received from the remote cluster. bytes/s
     */
    private double msgThroughputIn;

    /**
     * Total rate of messages delivered to the replication-subscriber. msg/s
     */
    private double msgRateOut;

    /**
     * Total throughput delivered to the replication-subscriber. bytes/s
     */
    private double msgThroughputOut;

    /**
     * Total rate of messages expired. msg/s
     */
    private double msgRateExpired;

    /**
     * Number of messages pending to be replicated to remote cluster
     */
    private long replicationBacklog;

    /**
     * is the replication-subscriber up and running to replicate to remote cluster
     */
    private boolean connected;

    /**
     * Time in seconds from the time a message was produced to the time when it is about to be replicated
     */
    private long replicationDelayInSeconds;

    /**
     * Address of incoming replication connection
     */
    private String inboundConnection;

    /**
     * Timestamp of incoming connection establishment time
     */
    private String inboundConnectedSince;

    /**
     * Address of outbound replication connection
     */
    private String outboundConnection;

    /**
     * Timestamp of outbound connection establishment time
     */
    private String outboundConnectedSince;

    private ArrayList<PulsarManagerConsumerStats> consumers;

}
