package com.ahcloud.edas.pulsar.core.domain.broker.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/27 15:10
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BrokerVO {

    /**
     * broker
     */
    private String broker;

    /**
     * 失败的域名
     */
    private List<String> failureDomains;
}
