package com.ahcloud.edas.pulsar.core.domain.namespace.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 10:40
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class NamespaceQuery extends PageQuery {

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 命名空间名称
     */
    private String namespaceName;
}
