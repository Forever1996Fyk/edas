package com.ahcloud.edas.pulsar.api.controller;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.pulsar.core.application.manager.BrokerManager;
import com.ahcloud.edas.pulsar.core.domain.broker.vo.BrokerVO;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/27 10:02
 **/
@RestController
@RequestMapping("/broker")
public class BrokerController {
    @Resource
    private BrokerManager brokerManager;

    /**
     * 查询broker集合
     * @param cluster
     * @return
     * @throws PulsarAdminException
     */
    @GetMapping("/list/{cluster}")
    public ResponseResult<List<BrokerVO>> listBrokers(@PathVariable("cluster") String cluster) throws PulsarAdminException {
        return ResponseResult.ofSuccess(brokerManager.listBrokers(cluster));
    }

    /**
     * 查询broker监控指标链接
     * @return
     */
    @GetMapping("/stats/metrics")
    public ResponseResult<String> getBrokerStatsMetrics() throws PulsarAdminException {
        return ResponseResult.ofSuccess(brokerManager.forwardBrokerStatsMetrics());
    }
}
