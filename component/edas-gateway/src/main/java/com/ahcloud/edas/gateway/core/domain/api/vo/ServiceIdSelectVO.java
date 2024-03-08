package com.ahcloud.edas.gateway.core.domain.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/5/29 18:35
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ServiceIdSelectVO {

    /**
     * 应用名
     */
    private String serviceId;

    /**
     * 环境变量
     */
    private String env;
}
