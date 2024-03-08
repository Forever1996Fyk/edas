package com.ahcloud.edas.rocketmq.core.application.helper;

import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.ResetOffsetForm;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.SubscribeAddForm;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.SubscribeUpdateForm;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.SubscriptionGroupConfigDetail;
import com.ahcloud.edas.rocketmq.core.infrastructure.constant.AclPermEnum;
import com.ahcloud.edas.rocketmq.core.infrastructure.repository.bean.RmqSubscribeAcl;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.ConsumerConfigInfo;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request.ResetOffsetRequest;
import com.ahcloud.edas.uaa.client.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.remoting.protocol.subscription.SubscriptionGroupConfig;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/3 17:17
 **/
public class RmqSubscribeHelper {

    /**
     * 数据转换
     * @param clusterNameList
     * @param brokerNameList
     * @param subscriptionGroupConfigDetail
     * @return
     */
    public static ConsumerConfigInfo convert(List<String> clusterNameList, List<String> brokerNameList, SubscriptionGroupConfigDetail subscriptionGroupConfigDetail) {
        ConsumerConfigInfo consumerConfigInfo = new ConsumerConfigInfo();
        consumerConfigInfo.setBrokerNameList(brokerNameList);
        consumerConfigInfo.setClusterNameList(clusterNameList);
        SubscriptionGroupConfig subscriptionGroupConfig = new SubscriptionGroupConfig();
        subscriptionGroupConfig.setGroupName(subscriptionGroupConfigDetail.getGroupName());
        subscriptionGroupConfig.setRetryMaxTimes(subscriptionGroupConfigDetail.getRetryMaxTimes());
        subscriptionGroupConfig.setRetryQueueNums(subscriptionGroupConfigDetail.getRetryQueueNums());
        subscriptionGroupConfig.setAttributes(subscriptionGroupConfigDetail.getAttributes());
        subscriptionGroupConfig.setBrokerId(subscriptionGroupConfigDetail.getBrokerId());
        subscriptionGroupConfig.setConsumeEnable(subscriptionGroupConfigDetail.isConsumeEnable());
        subscriptionGroupConfig.setConsumeBroadcastEnable(subscriptionGroupConfigDetail.isConsumeBroadcastEnable());
        subscriptionGroupConfig.setConsumeFromMinEnable(subscriptionGroupConfigDetail.isConsumeFromMinEnable());
        subscriptionGroupConfig.setConsumeMessageOrderly(subscriptionGroupConfigDetail.isConsumeMessageOrderly());
        subscriptionGroupConfig.setNotifyConsumerIdsChangedEnable(subscriptionGroupConfigDetail.isNotifyConsumerIdsChangedEnable());
        subscriptionGroupConfig.setWhichBrokerWhenConsumeSlowly(subscriptionGroupConfigDetail.getWhichBrokerWhenConsumeSlowly());
        subscriptionGroupConfig.setConsumeTimeoutMinute(subscriptionGroupConfigDetail.getConsumeTimeoutMinute());
        consumerConfigInfo.setSubscriptionGroupConfig(subscriptionGroupConfig);
        return consumerConfigInfo;
    }

    /**
     * 数据转换
     * @param consumerConfigInfo
     * @return
     */
    public static SubscriptionGroupConfigDetail convert(ConsumerConfigInfo consumerConfigInfo) {
        SubscriptionGroupConfig subscriptionGroupConfig = consumerConfigInfo.getSubscriptionGroupConfig();
        return SubscriptionGroupConfigDetail.builder()
                .attributes(subscriptionGroupConfig.getAttributes())
                .brokerId(subscriptionGroupConfig.getBrokerId())
                .consumeBroadcastEnable(subscriptionGroupConfig.isConsumeBroadcastEnable())
                .consumeEnable(subscriptionGroupConfig.isConsumeEnable())
                .consumeFromMinEnable(subscriptionGroupConfig.isConsumeFromMinEnable())
                .consumeMessageOrderly(subscriptionGroupConfig.isConsumeMessageOrderly())
                .consumeTimeoutMinute(subscriptionGroupConfig.getConsumeTimeoutMinute())
                .groupName(subscriptionGroupConfig.getGroupName())
                .retryQueueNums(subscriptionGroupConfig.getRetryQueueNums())
                .retryMaxTimes(subscriptionGroupConfig.getRetryMaxTimes())
                .notifyConsumerIdsChangedEnable(subscriptionGroupConfig.isNotifyConsumerIdsChangedEnable())
                .whichBrokerWhenConsumeSlowly(subscriptionGroupConfig.getWhichBrokerWhenConsumeSlowly())
                .build();
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static RmqSubscribeAcl convert(SubscribeAddForm form, AclPermEnum topicPerm, AclPermEnum groupPerm) {
        RmqSubscribeAcl rmqSubscribeAcl = new RmqSubscribeAcl();
        rmqSubscribeAcl.setAppId(form.getAppId());
        rmqSubscribeAcl.setAppCode(form.getAppCode());
        rmqSubscribeAcl.setEnv(form.getEnv());
        rmqSubscribeAcl.setBrokerNames(StringUtils.join(form.getBrokerNameList(), ","));
        rmqSubscribeAcl.setTopicId(form.getTopicId());
        rmqSubscribeAcl.setTopicPerm(topicPerm.getPerm());
        rmqSubscribeAcl.setGroupPerm(groupPerm.getPerm());
        rmqSubscribeAcl.setCreator(UserUtils.getUserNameBySession());
        rmqSubscribeAcl.setModifier(UserUtils.getUserNameBySession());
        return rmqSubscribeAcl;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static RmqSubscribeAcl convert(SubscribeUpdateForm form, AclPermEnum topicPerm, AclPermEnum groupPerm) {
        RmqSubscribeAcl rmqSubscribeAcl = new RmqSubscribeAcl();
        rmqSubscribeAcl.setBrokerNames(JsonUtils.toJsonString(form.getBrokerNameList()));
        rmqSubscribeAcl.setTopicPerm(topicPerm.getPerm());
        rmqSubscribeAcl.setGroupPerm(groupPerm.getPerm());
        rmqSubscribeAcl.setModifier(UserUtils.getUserNameBySession());
        return rmqSubscribeAcl;
    }

    /**
     * 数据转换
     * @param topicName
     * @param form
     * @return
     */
    public static ResetOffsetRequest convert(String topicName, ResetOffsetForm form) {
        ResetOffsetRequest request = new ResetOffsetRequest();
        request.setForce(true);
        request.setResetTime(Objects.isNull(form.getResetTime()) ? -1 : form.getResetTime().getTime());
        request.setTopic(topicName);
        request.setConsumerGroupList(Collections.singletonList(form.getGroupName()));
        return request;
    }

    public static String rebuildGroupName(String groupName, String appCode, String env) {
        return appCode + "-" + groupName + "-subscription-" + env;
    }
}
