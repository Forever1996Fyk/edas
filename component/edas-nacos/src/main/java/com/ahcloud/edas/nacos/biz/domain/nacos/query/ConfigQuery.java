package com.ahcloud.edas.nacos.biz.domain.nacos.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/7 00:05
 **/
@Data
public class ConfigQuery extends PageQuery {

    /**
     * id
     */
    private Long id;

    /**
     * 历史配置id
     */
    private Long historyId;

    /**
     * appId
     */
    private Long appId;

    /**
     * dataId
     */
    private String dataId;

    /**
     * 分组
     */
    private String group;

    /**
     * 命名空间
     */
    private String namespace;
}
