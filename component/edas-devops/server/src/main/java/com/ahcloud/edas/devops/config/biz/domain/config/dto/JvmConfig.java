package com.ahcloud.edas.devops.config.biz.domain.config.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/23 18:26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JvmConfig {
    /**
     * jvm最小堆内存
     * 1024M/1G
     */
    private String xms;

    /**
     * jvm最大堆内存
     * 2048M/2G
     */
    private String xmx;

    /**
     * gc类型
     * +UseG1GC
     */
    private String gc;

    /**
     * 开启普通对象指针压缩 压缩对象Klass pointer 减少运行内存 减少gc耗时
     * +UseCompressedOops
     */
    private String compressed;
}
