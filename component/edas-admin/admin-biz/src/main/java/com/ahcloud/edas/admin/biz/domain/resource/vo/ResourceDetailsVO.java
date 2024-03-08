package com.ahcloud.edas.admin.biz.domain.resource.vo;

import com.ahcloud.edas.admin.biz.domain.resource.ResourceConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 16:15
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDetailsVO {

    /**
     * id
     */
    private Long id;

    /**
     * appId
     */
    private Long appId;

    /**
     * app编码
     */
    private String appCode;

    /**
     * 环境变量
     */
    private String env;

    /**
     * 资源类型
     */
    private Integer type;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源配置
     */
    private ResourceConfig resourceConfig;

    /**
     * 资源地址
     */
    private String url;

    /**
     * 资源地址掩码
     */
    private String urlMask;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 端口号掩码
     */
    private String portMask;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户名掩码
     */
    private String usernameMask;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码掩码
     */
    private String passwordMask;
}
