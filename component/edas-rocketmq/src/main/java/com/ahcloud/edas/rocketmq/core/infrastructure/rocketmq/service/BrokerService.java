package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/19 16:49
 **/
public interface BrokerService {

    /**
     * 获取broker nam列表e
     * @return
     */
    List<String> getBrokerNameList();
}
