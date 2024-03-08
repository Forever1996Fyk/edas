package com.ahcloud.edas.rocketmq.core.infrastructure.config;

import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.admin.MQAdminFactory;
import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.admin.MQAdminPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.rocketmq.tools.admin.MQAdminExt;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 09:28
 **/
@Configuration
@EnableConfigurationProperties(RMQProperties.class)
public class RocketMqConfiguration {

    @Bean
    public GenericObjectPool<MQAdminExt> mqAdminExtPool(RMQProperties properties) {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setTestWhileIdle(true);
        genericObjectPoolConfig.setMaxWaitMillis(10000);
        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(20000);
        MQAdminPooledObjectFactory mqAdminPooledObjectFactory = new MQAdminPooledObjectFactory();
        MQAdminFactory mqAdminFactory = new MQAdminFactory(properties);
        mqAdminPooledObjectFactory.setMqAdminFactory(mqAdminFactory);
        return new GenericObjectPool<MQAdminExt>(
                mqAdminPooledObjectFactory,
                genericObjectPoolConfig);
    }
}
