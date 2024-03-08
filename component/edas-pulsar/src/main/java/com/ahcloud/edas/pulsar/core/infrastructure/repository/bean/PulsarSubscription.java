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
 * pulsar subscription管理
 * </p>
 *
 * @author auto_generation
 * @since 2024-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pulsar_subscription")
public class PulsarSubscription implements Serializable {

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
     * topicId
     */
    private Long topicId;

    /**
     * topic名称
     */
    private String topicName;

    /**
     * 订阅名称
     */
    private String subscriptionName;

    /**
     * 是否自动创建重试和死信队列
     */
    @TableField(value = "is_auto_retry_dead")
    private Integer autoRetryDead;

    /**
     * 说明
     */
    private String description;

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
