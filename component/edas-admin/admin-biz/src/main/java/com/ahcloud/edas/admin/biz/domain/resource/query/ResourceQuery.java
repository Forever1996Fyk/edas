package com.ahcloud.edas.admin.biz.domain.resource.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 16:27
 **/
@Data
public class ResourceQuery extends PageQuery {

    /**
     * appId
     */
    private Long appId;
}
