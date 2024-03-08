package com.ahcloud.edas.admin.biz.domain.resource.form;

import com.ahcloud.edas.admin.biz.domain.resource.ResourceConfig;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 16:08
 **/
@Data
public class ResourceAddForm {

    /**
     * appId
     */
    @NotNull(message = "appId不能为空")
    private Long appId;

    /**
     * 资源类型
     */
    @NotNull(message = "资源类型不能为空")
    private Integer type;

    /**
     * 资源名称
     */
    @NotEmpty(message = "资源名称不能为空")
    private String name;

    /**
     * 资源配置
     */
    private ResourceConfig resourceConfig;

    /**
     * 资源地址
     */
    @NotEmpty(message = "资源地址不能为空")
    private String url;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
