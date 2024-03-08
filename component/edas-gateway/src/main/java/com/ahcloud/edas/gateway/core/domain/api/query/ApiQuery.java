package com.ahcloud.edas.gateway.core.domain.api.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/2/6 11:19
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiQuery extends PageQuery {

    /**
     * api编码
     */
    private String apiCode;

    /**
     * api名称
     */
    private String name;

    /**
     * 服务id
     */
    private String serviceId;
}
