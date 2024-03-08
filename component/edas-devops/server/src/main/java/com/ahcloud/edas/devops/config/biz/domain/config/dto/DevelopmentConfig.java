package com.ahcloud.edas.devops.config.biz.domain.config.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/8 09:41
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevelopmentConfig {

    /**
     * jvm配置
     */
    private JvmConfig jvmConfig;

    /**
     * nacos配置
     */
    private NacosConfig nacosConfig;
}
