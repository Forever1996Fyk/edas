package com.ahcloud.edas.gateway.core.domain.api.bo;

import com.ahcloud.edas.gateway.core.infrastructure.constant.enums.ApiHttpMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/2/3 10:01
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiRegisterBO {

    /**
     * 请求路径
     */
    private String apiPath;

    /**
     * 服务id
     */
    private String serviceId;

    /**
     * http method
     */
    private ApiHttpMethodEnum apiHttpMethodEnum;

    /**
     * 全限定名
     */
    private String qualifiedName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * RequestMapping consume
     */
    private String consume;

    /**
     * RequestMapping produce
     */
    private String produce;

    /**
     * 环境
     */
    private String env;
}
