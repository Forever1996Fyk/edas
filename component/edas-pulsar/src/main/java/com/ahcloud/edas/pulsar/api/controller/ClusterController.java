package com.ahcloud.edas.pulsar.api.controller;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.pulsar.core.application.manager.ClusterManager;
import com.ahcloud.edas.pulsar.core.domain.cluster.vo.ClusterVO;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/27 15:15
 **/
@RestController
@RequestMapping("/cluster")
public class ClusterController {

    @Resource
    private ClusterManager clusterManager;

    /**
     * 查询集群列表
     * @return
     * @throws PulsarAdminException
     */
    @GetMapping("/list")
    public ResponseResult<List<ClusterVO>> listClusters() throws PulsarAdminException {
        return ResponseResult.ofSuccess(clusterManager.listClusters());
    }
}
