package com.ahcloud.edas.admin.biz.domain.resource;

import lombok.Data;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 16:09
 **/
@Data
public class ResourceConfig {

    /**
     * 核数
     */
    private Integer core;

    /**
     * 内存
     */
    private Integer ram;

    /**
     * 存储空间
     */
    private Integer storageSpace;

    /**
     * 来源
     */
    private Integer source;

    /**
     * 最大连接数
     */
    private Integer maxConnectNum;
}
