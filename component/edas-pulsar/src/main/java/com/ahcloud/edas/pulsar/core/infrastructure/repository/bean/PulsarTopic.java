package com.ahcloud.edas.pulsar.core.infrastructure.repository.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * pulsar topic管理
 * </p>
 *
 * @author auto_generation
 * @since 2024-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pulsar_topic")
public class PulsarTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)

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
     * env
     */
    private String env;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 命名空间id
     */
    private Long namespaceId;

    /**
     * 命名空间
     */
    private String namespaceName;

    /**
     * topic名称
     */
    private String topicName;

    /**
     * topic完整名称
     */
    private String topicFullName;

    /**
     * 是否持久化
     */
    @TableField("is_persistent")
    private Integer persistent;

    /**
     * 是否分区
     */
    @TableField("is_partition")
    private Integer partitionValue;

    /**
     * 分区数
     */
    private Integer partitionNum;

    /**
     * 策略
     */
    private String policiesJson;

    /**
     * 说明
     */
    private String description;

    /**
     * 创建来源
     */
    private Integer source;

    /**
     * 备注
     */
    private String remark;

    /**
     * 行记录创建者
     */
    private String creator;

    /**
     * 行记录最近更新人
     */
    private String modifier;

    /**
     * 行记录创建时间
     */
    private Date createdTime;

    /**
     * 行记录最近修改时间
     */
    private Date modifiedTime;

    /**
     * 拓展字段
     */
    private String extension;

    /**
     * 行版本号
     */
    @Version
    private Long version;

    /**
     * 是否删除
     */
    private Long deleted;

}
