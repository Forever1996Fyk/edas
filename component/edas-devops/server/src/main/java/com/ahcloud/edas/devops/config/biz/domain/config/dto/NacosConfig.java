package com.ahcloud.edas.devops.config.biz.domain.config.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/23 18:28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NacosConfig {

    /**
     * nacos地址
     */
    private String nacosUrl;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
