package com.ahcloud.edas.nacos.biz.infrastructure.nacos;

import com.ahcloud.edas.nacos.biz.infrastructure.config.NacosProperties;
import com.alibaba.nacos.common.http.HttpClientConfig;
import com.alibaba.nacos.common.http.client.NacosRestTemplate;
import com.alibaba.nacos.common.http.client.request.JdkHttpClientRequest;
import org.slf4j.LoggerFactory;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 16:48
 **/
public class NacosConnector {

    private final NacosProperties properties;
    private final NacosRestTemplate restTemplate;

    public NacosConnector(NacosProperties properties) {
        this.properties = properties;
        HttpClientConfig httpClientConfig = HttpClientConfig.builder().setConTimeOutMillis(properties.getConTimeOutMillis())
                .setReadTimeOutMillis(properties.getReadTimeOutMillis())
                .build();
        JdkHttpClientRequest request = new JdkHttpClientRequest(httpClientConfig);
        this.restTemplate = new NacosRestTemplate(LoggerFactory.getLogger("root"), request);
    }

    public NacosProperties getProperties() {
        return properties;
    }

    public NacosRestTemplate getRestTemplate() {
        return restTemplate;
    }
}
