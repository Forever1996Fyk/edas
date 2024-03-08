package com.ahcloud.edas.pulsar.core.infrastructure.pulsar.jwt;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/20 15:09
 **/
public interface JwtService {

    /**
     * 创建broker token
     * @param role
     * @param expiryTime
     * @return
     */
    String createBrokerToken(String role, String expiryTime);
}
