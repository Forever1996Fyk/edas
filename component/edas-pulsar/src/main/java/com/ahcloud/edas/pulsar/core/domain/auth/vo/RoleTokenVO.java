package com.ahcloud.edas.pulsar.core.domain.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/20 14:57
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleTokenVO {

    /**
     * 认证token
     */
    private String token;
}
