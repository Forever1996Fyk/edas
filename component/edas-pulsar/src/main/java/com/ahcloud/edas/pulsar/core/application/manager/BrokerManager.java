package com.ahcloud.edas.pulsar.core.application.manager;

import com.ahcloud.edas.pulsar.core.domain.broker.vo.BrokerVO;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.admin.PulsarAdminService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.common.policies.data.FailureDomain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/27 15:05
 **/
@Slf4j
@Component
public class BrokerManager {
    @Resource
    private PulsarAdminService pulsarAdminService;

    /**
     * 查询broker列表
     * @param cluster
     * @return
     * @throws PulsarAdminException
     */
    public List<BrokerVO> listBrokers(String cluster) throws PulsarAdminException {
        Map<String, FailureDomain> failureDomains = pulsarAdminService.clusters().getFailureDomains(cluster);
        List<String> activeBrokers = pulsarAdminService.brokers().getActiveBrokers(cluster);
        return activeBrokers.stream().map(
                broker -> BrokerVO.builder()
                        .broker(broker)
                        .failureDomains(this.getFailureDomain(broker, failureDomains))
                        .build()
        ).collect(Collectors.toList());
    }

    private List<String> getFailureDomain(String broker, Map<String, FailureDomain> failureDomains) {
        List<String> failureDomainsList = Lists.newArrayList();
        for (String failureDomain: failureDomains.keySet()) {
            FailureDomain domain = failureDomains.get(failureDomain);
            if (domain.getBrokers().contains(broker)) {
                failureDomainsList.add(failureDomain);
            }
        }
        return failureDomainsList;
    }

    /**
     *跳转broker stats metrics 链接
     * @return
     * @throws PulsarAdminException
     */
    public String forwardBrokerStatsMetrics() throws PulsarAdminException {
        return pulsarAdminService.brokerStats().getMetrics();
    }
}
