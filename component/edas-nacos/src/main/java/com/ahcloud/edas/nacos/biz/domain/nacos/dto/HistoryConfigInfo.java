package com.ahcloud.edas.nacos.biz.domain.nacos.dto;

import com.ahcloud.edas.common.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 22:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryConfigInfo {

    /**
     * 历史配置id
     */
    private String id;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 最新id
     */
    private Integer lastId;

    /**
     * 配置名
     */
    private String dataId;

    /**
     * 分组
     */
    private String group;

    /**
     * 租户
     */
    private String tenant;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 配置md5
     */
    private String md5;

    /**
     * 配置内容
     */
    private String content;

    /**
     * 源ip
     */
    private String srcIp;

    /**
     * 源用户
     */
    private String srcUser;

    /**
     * 操作类型
     */
    private String opType;

    /**
     * 操作类型
     */
    private String opTypeName;

    /**
     * 创建时间
     */
    private String createdTime;

    /**
     * 上次修改时间
     */
    private String lastModifiedTime;

    /**
     * 创建时间
     */
    private String createdDateTime;

    /**
     * 上次修改时间
     */
    private String lastModifiedDateTime;

    /**
     * 加密密钥
     */
    private String encryptedDataKey;
}
