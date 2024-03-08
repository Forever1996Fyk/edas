package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service;

import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.ConsumerGroupRollBackStat;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.GroupConsumeInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.ConsumerConfigInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.DeleteSubGroupRequest;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.ResetOffsetRequest;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/10 15:14
 **/
public interface ConsumerService {

    /**
     * 根据订阅组名称查询消费组信息
     * @param groupNameList
     * @return
     */
    List<GroupConsumeInfo> queryGroupList(Collection<String> groupNameList);

    /**
     * 根据名称查询订阅组信息
     * @param consumerGroup
     * @return
     */
    GroupConsumeInfo queryGroup(String consumerGroup);

    /**
     * 新增或更新订阅组配置
     * @param consumerConfigInfo
     * @return
     */
    boolean createAndUpdateSubscriptionGroupConfig(ConsumerConfigInfo consumerConfigInfo);

    /**
     * 删除订阅组
     * @param brokerNameList
     * @param deleteSubGroupRequest
     * @return
     */
    boolean deleteSubGroup(List<String> brokerNameList, DeleteSubGroupRequest deleteSubGroupRequest);

    /**
     * 重置消费点位
     * @param resetOffsetRequest
     * @return
     */
    Map<String, ConsumerGroupRollBackStat> resetOffset(ResetOffsetRequest resetOffsetRequest);

    /**
     * 根据订阅组名称查询详情
     * @param groupName
     * @return
     */
    ConsumerConfigInfo examineSubscriptionGroupConfig(String groupName);
}
