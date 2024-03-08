package com.ahcloud.edas.pulsar.core.infrastructure.pulsar.admin;

import org.apache.pulsar.client.admin.Bookies;
import org.apache.pulsar.client.admin.BrokerStats;
import org.apache.pulsar.client.admin.Brokers;
import org.apache.pulsar.client.admin.Clusters;
import org.apache.pulsar.client.admin.Namespaces;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.Tenants;
import org.apache.pulsar.client.admin.TopicPolicies;
import org.apache.pulsar.client.admin.Topics;

import java.util.Map;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/18 15:59
 **/
public interface PulsarAdminService {

    /**
     * 获取admin
     * @return
     */
    PulsarAdmin getPulsarAdmin();

    /**
     * 获取broker状态操作
     * @return
     */
    BrokerStats brokerStats();

    /**
     * 集群操作
     * @return
     */
    Clusters clusters();

    /**
     * brokers操作
     * @return
     */
    Brokers brokers();

    /**
     * bookies操作
     * @return
     */
    Bookies bookies();

    /**
     * 租户操作
     * @return
     */
    Tenants tenants();

    /**
     * 命名空间操作
     * @return
     */
    Namespaces namespaces();

    /**
     * topic操作
     * @return
     */
    Topics topics();

    /**
     * topic操作
     *
     * @param isGlobal
     * @return
     */
    TopicPolicies topicPolicies(boolean isGlobal);

    /**
     * 获取认证头
     * @return
     */
    Map<String, String> getAuthHeader();
}
