package com.ahcloud.edas.pulsar.core.domain.tenant.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 09:11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantQuery extends PageQuery {

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 类型
     */
    private Integer type;
}
