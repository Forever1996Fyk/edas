package com.ahcloud.edas.nacos.biz.domain.nacos.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/7 00:06
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigVO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * appId
     */
    private Long appId;

    /**
     * app编码
     */
    private String appCode;

    /**
     * 环境变量
     */
    private String env;

    /**
     * 租户
     */
    private String tenant;

    /**
     * 配置id
     */
    private String dataId;

    /**
     * 配置分组
     */
    private String bizGroup;

    /**
     * 配置类型
     */
    private String type;

    /**
     * 内容
     */
    private String content;

    /**
     * 行记录创建者
     */
    private String creator;

    /**
     * 行记录创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss")
    private Date createdTime;
}
