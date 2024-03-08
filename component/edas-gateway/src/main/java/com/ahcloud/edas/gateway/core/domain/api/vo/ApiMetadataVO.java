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
 * @create: 2023/2/4 10:14
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiMetadataVO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 请求路径
     */
    private String apiPath;

    /**
     * appName
     */
    private String appName;

    /**
     * 服务id
     */
    private String serviceId;

    /**
     * 方法类型
     */
    private String httpMethod;

    /**
     * 全限定名 + 方法名
     */
    private String className;

    /**
     * 全限定名
     */
    private String qualifiedName;

    /**
     * 方法名
     */
    private String methodName;

}
