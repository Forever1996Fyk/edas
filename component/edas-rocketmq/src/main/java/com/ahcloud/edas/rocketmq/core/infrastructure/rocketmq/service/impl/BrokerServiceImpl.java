package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.impl;

import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.rocketmq.core.infrastructure.config.RMQProperties;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.RmqRetCodeEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.AbstractCommonService;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.BrokerService;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.common.TopicConfig;
import org.apache.rocketmq.remoting.exception.RemotingConnectException;
import org.apache.rocketmq.remoting.exception.RemotingSendRequestException;
import org.apache.rocketmq.remoting.exception.RemotingTimeoutException;
import org.apache.rocketmq.remoting.protocol.body.ClusterInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/19 16:51
 **/
@Slf4j
@Service
public class BrokerServiceImpl extends AbstractCommonService implements BrokerService {
    @Resource
    private RMQProperties properties;

    @Override
    public List<String> getBrokerNameList() {
        try {
            ClusterInfo clusterInfo = mqAdminExt.examineBrokerClusterInfo();
            List<String> result = Lists.newArrayList();
            List<String> clusterNameList = properties.getClusterNameList();
            for (String clusterName : clusterNameList) {
                Map<String, Set<String>> clusterAddrTable = clusterInfo.getClusterAddrTable();
                Set<String> brokerNameSet = clusterAddrTable.get(clusterName);
                result.addAll(brokerNameSet);
            }
            return result;
        } catch (Exception err) {
            log.error("BrokerServiceImpl[getBrokerNameList] get brokerName List error, cause is {}" , Throwables.getStackTraceAsString(err));
            throw new BizException(RmqRetCodeEnum.BROKER_NAME_LIST_FAILED);
        }
    }
}
