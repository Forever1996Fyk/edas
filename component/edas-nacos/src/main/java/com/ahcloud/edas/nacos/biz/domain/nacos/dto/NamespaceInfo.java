package com.ahcloud.edas.nacos.biz.domain.nacos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 23:47
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NamespaceInfo {

    /**
     * 命名空间id
     */
    private String namespace;

    /**
     * 命名空间名称
     */
    private String namespaceShowName;

    /**
     * 命名空间描述
     */
    private String namespaceDesc;

    /**
     * 命名空间容量
     */
    private Integer quota;

    /**
     * 配置数量
     */
    private Integer configCount;

    /**
     * 类型
     * 0：全局
     * 1：默认私有
     * 2：自定义
     */
    private Integer type;
}
