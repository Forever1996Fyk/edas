package com.ahcloud.edas.pulsar.core.infrastructure.pulsar.admin.impl;

import com.ahcloud.edas.pulsar.core.infrastructure.configuration.PulsarProperties;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.admin.PulsarAdminService;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.admin.Bookies;
import org.apache.pulsar.client.admin.BrokerStats;
import org.apache.pulsar.client.admin.Brokers;
import org.apache.pulsar.client.admin.Clusters;
import org.apache.pulsar.client.admin.Namespaces;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminBuilder;
import org.apache.pulsar.client.admin.Tenants;
import org.apache.pulsar.client.admin.TopicPolicies;
import org.apache.pulsar.client.admin.Topics;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.Map;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/18 16:02
 **/
@Slf4j
public class PulsarAdminServiceImpl implements PulsarAdminService {

    private final PulsarAdmin pulsarAdmin;
    private final PulsarProperties properties;

    public PulsarAdminServiceImpl(PulsarProperties properties) {
        this.properties = properties;
        this.pulsarAdmin = createPulsarAdmin();
    }

    @Override
    public PulsarAdmin getPulsarAdmin() {
        return pulsarAdmin;
    }

    @Override
    public BrokerStats brokerStats() {
        return getPulsarAdmin().brokerStats();
    }

    @Override
    public Clusters clusters() {
        return getPulsarAdmin().clusters();
    }

    @Override
    public Brokers brokers() {
        return getPulsarAdmin().brokers();
    }

    @Override
    public Bookies bookies() {
        return getPulsarAdmin().bookies();
    }

    @Override
    public Tenants tenants() {
        return getPulsarAdmin().tenants();
    }

    @Override
    public Namespaces namespaces() {
        return getPulsarAdmin().namespaces();
    }

    @Override
    public Topics topics() {
        return getPulsarAdmin().topics();
    }

    @Override
    public TopicPolicies topicPolicies(boolean isGlobal) {
        return getPulsarAdmin().topicPolicies(isGlobal);
    }

    @Override
    public Map<String, String> getAuthHeader() {
        return null;
    }

    /**
     * 创建pulsarAdmin
     * @return
     */
    public PulsarAdmin createPulsarAdmin() {
        try {
            PulsarAdminBuilder pulsarAdminBuilder = PulsarAdmin.builder();
            pulsarAdminBuilder.serviceHttpUrl(properties.getServiceHttpUrl());
            if (StringUtils.isNotBlank(properties.getToken())) {
                pulsarAdminBuilder.authentication(
                        AuthenticationFactory.token(properties.getToken())
                );
            }
            pulsarAdminBuilder.allowTlsInsecureConnection(properties.isAllowTlsInsecureConnection());
            pulsarAdminBuilder.enableTlsHostnameVerification(properties.isEnableTlsHostnameVerification());
            pulsarAdminBuilder.useKeyStoreTls(properties.isUseKeyStoreTls());
            return pulsarAdminBuilder.build();
        } catch (PulsarClientException e) {
            log.error("PulsarAdminServiceImpl[createPulsarAdmin] create pulsar admin failed, reason is {}", Throwables.getStackTraceAsString(e));
            throw new RuntimeException(e.getMessage());
        }
    }
}
