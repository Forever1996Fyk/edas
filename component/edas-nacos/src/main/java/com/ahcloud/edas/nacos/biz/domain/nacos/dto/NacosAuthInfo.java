package com.ahcloud.edas.nacos.biz.domain.nacos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 22:22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NacosAuthInfo {

    /**
     * 认证token
     */
    private String accessToken;

    /**
     * token过期时间
     */
    private long tokenTtl;

    /**
     * 是否全局管理员
     */
    private boolean globalAdmin;

    /**
     * 用户名
     */
    private String username;
}
