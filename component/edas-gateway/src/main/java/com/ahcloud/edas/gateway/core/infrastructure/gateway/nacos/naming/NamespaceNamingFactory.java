package com.ahcloud.edas.gateway.core.infrastructure.gateway.nacos.naming;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;

import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/8 16:05
 **/
public class NamespaceNamingFactory {
    /**
     * Create a new naming service.
     *
     * @param serverList server list
     * @return new naming service
     * @throws NacosException nacos exception
     */
    public static NamingService createNamingService(String serverList) throws NacosException {
        try {
            Class<?> driverImplClass = Class.forName("com.ahcloud.edas.gateway.core.infrastructure.gateway.nacos.naming.NamespaceNacosNamingService");
            Constructor constructor = driverImplClass.getConstructor(String.class);
            return (NamingService) constructor.newInstance(serverList);
        } catch (Throwable e) {
            throw new NacosException(NacosException.CLIENT_INVALID_PARAM, e);
        }
    }

    /**
     * Create a new naming service.
     *
     * @param properties naming service properties
     * @return new naming service
     * @throws NacosException nacos exception
     */
    public static NamingService createNamingService(String namespace, Properties properties) throws NacosException {
        try {
            Class<?> driverImplClass = Class.forName("com.ahcloud.edas.gateway.core.infrastructure.gateway.nacos.naming.NamespaceNacosNamingService");
            Constructor constructor = driverImplClass.getConstructor(String.class, Properties.class);
            return (NamingService) constructor.newInstance(namespace, properties);
        } catch (Throwable e) {
            throw new NacosException(NacosException.CLIENT_INVALID_PARAM, e);
        }
    }
}
