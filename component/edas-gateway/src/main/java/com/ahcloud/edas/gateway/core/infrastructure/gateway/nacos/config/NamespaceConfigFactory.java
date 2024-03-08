package com.ahcloud.edas.gateway.core.infrastructure.gateway.nacos.config;

import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;

import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/7 22:32
 **/
public class NamespaceConfigFactory {

    /**
     * Create Config.
     *
     * @param properties init param
     * @return ConfigService
     * @throws NacosException Exception
     */
    public static NamespaceNacosConfigService createConfigService(Properties properties) throws NacosException {
        try {
            Class<?> driverImplClass = Class.forName("com.ahcloud.edas.gateway.core.infrastructure.gateway.nacos.config.impl.EnvNamespaceNacosConfigServiceImpl");
            Constructor constructor = driverImplClass.getConstructor(Properties.class);
            return (NamespaceNacosConfigService) constructor.newInstance(properties);
        } catch (Throwable e) {
            throw new NacosException(NacosException.CLIENT_INVALID_PARAM, e);
        }
    }

    /**
     * Create Config.
     *
     * @param serverAddr serverList
     * @return Config
     * @throws NacosException create configService failed Exception
     */
    public static NamespaceNacosConfigService createConfigService(String serverAddr) throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        return createConfigService(properties);
    }
}
