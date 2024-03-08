package com.ahcloud.edas.pulsar.core.application.manager;

import com.ahcloud.edas.pulsar.core.domain.broker.vo.BrokerVO;
import com.ahcloud.edas.pulsar.core.domain.cluster.vo.ClusterVO;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.admin.PulsarAdminService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.common.policies.data.ClusterData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/27 15:17
 **/
@Slf4j
@Component
public class ClusterManager {

    @Resource
    private BrokerManager brokerManager;
    @Resource
    private PulsarAdminService pulsarAdminService;

    /**
     * 查询集群列表
     * @return
     * @throws PulsarAdminException
     */
    public List<ClusterVO> listClusters() throws PulsarAdminException {
        List<String> clusters = pulsarAdminService.clusters().getClusters();
        List<ClusterVO> clusterVOS = Lists.newArrayList();
        for (String cluster : clusters) {
            List<BrokerVO> brokerVOList = brokerManager.listBrokers(cluster);
            ClusterData clusterData = pulsarAdminService.clusters().getCluster(cluster);
            clusterVOS.add(
                    ClusterVO.builder()
                            .cluster(cluster)
                            .brokersNum(brokerVOList.size())
                            .serviceUrl(clusterData.getServiceUrl())
                            .serviceUrlTls(clusterData.getServiceUrlTls())
                            .brokerServiceUrl(clusterData.getBrokerServiceUrl())
                            .brokerServiceUrlTls(clusterData.getBrokerServiceUrlTls())
                            .build()
            );
        }
        return clusterVOS;
    }
}
