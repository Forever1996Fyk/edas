package com.ahcloud.edas.admin.biz.domain.resource.vo;

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
public class ResourceVO {

    /**
     * 资源id
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
     * 资源类型
     */
    private String typeName;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源规格
     */
    private String resourceSpec;

    /**
     * 资源地址掩码
     */
    private String urlMask;

    /**
     * 端口号掩码
     */
    private String portMask;

    /**
     * 用户名掩码
     */
    private String usernameMask;

    /**
     * 密码掩码
     */
    private String passwordMask;
}
