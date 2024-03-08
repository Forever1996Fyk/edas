package com.ahcloud.edas.pulsar.core.domain.bookie.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/27 10:12
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class BookieQuery extends PageQuery {

    /**
     * 集群
     */
    private String cluster;
}
