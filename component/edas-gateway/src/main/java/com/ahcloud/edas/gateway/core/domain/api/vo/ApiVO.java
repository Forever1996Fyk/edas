package com.ahcloud.edas.gateway.core.domain.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/2/4 11:14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiVO {
    /**
     * 主键id
     */
    private Long id;

    /**
     * appId
     */
    private Long appId;

    /**
     * appName
     */
    private String appName;

    /**
     * 接口编码
     */
    private String apiCode;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口类型
     */
    private Integer apiType;

    /**
     * 请求路径
     */
    private String apiPath;

    /**
     * 服务id
     */
    private String serviceId;

    /**
     * 全限定名 + 方法名
     */
    private String className;

    /**
     * http方式
     */
    private String httpMethod;

    /**
     * 是否认证
     */
    private Integer auth;

    /**
     * 读写类型
     */
    private Integer readOrWrite;

    /**
     * api描述
     */
    private String apiDesc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /**
     * 开发环境状态
     */
    private Integer dev;

    /**
     * 联调环境状态
     */
    private Integer test;

    /**
     * 测试环境状态
     */
    private Integer sit;

    /**
     * 预发环境状态
     */
    private Integer pre;

    /**
     * 生产环境状态
     */
    private Integer prod;
}
