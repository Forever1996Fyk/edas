package com.ahcloud.edas.pulsar.core.domain.broker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/3/5 09:22
 **/
@Getter
@Setter
@NoArgsConstructor
public class PulsarManagerPublisherStats {

    /**
     * Total rate of messages published by this publisher. msg/s
     */
    private double msgRateIn;

    /**
     * Total throughput of messages published by this publisher. byte/s
     */
    private double msgThroughputIn;

    /**
     * Average message size published by this publisher
     */
    private double averageMsgSize;

    private String address;

    /**
     * Id of this publisher
     */
    private long producerId;

    private String producerName;

    private String connectedSince;

    private String clientVersion;

    private Map<String, String> metadata;
}

