package com.ahcloud.edas.pulsar.core.domain.tenant.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/28 09:08
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TenantSelectVO {

    /**
     * 租户id
     */
    private Long id;

    /**
     * 租户名称
     */
    private String name;
}
