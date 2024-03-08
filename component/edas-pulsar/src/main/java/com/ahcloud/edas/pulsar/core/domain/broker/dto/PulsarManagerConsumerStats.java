package com.ahcloud.edas.pulsar.core.domain.broker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/3/5 09:23
 **/
@Getter
@Setter
@NoArgsConstructor
public class PulsarManagerConsumerStats {

    private String address;

    private String connectedSince;

    private String clientVersion;

    /**
     * Total rate of messages delivered to the consumer. msg/s
     */
    private double msgRateOut;

    /**
     * Total throughput delivered to the consumer. bytes/s
     */
    private double msgThroughputOut;

    /**
     * Total rate of messages redelivered by this consumer. msg/s
     */
    private double msgRateRedeliver;

    /**
     * Name of the consumer
     */
    private String consumerName;

    /**
     * Number of available message permits for the consumer
     */
    private int availablePermits;

    /**
     * Metadata (key/value strings) associated with this consumer
     */
    private HashMap<String, String> metadata;
}
