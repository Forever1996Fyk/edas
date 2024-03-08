package com.ahcloud.edas.rocketmq.core.infrastructure.repository.bean;

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
 * rocketmq topic配置
 * </p>
 *
 * @author auto_generation
 * @since 2023-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("rmq_topic")
public class RmqTopic implements Serializable {

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
     * brokers
     */
    private String brokerNames;

    /**
     * topic名称
     */
    private String topicName;

    /**
     * 写队列数
     */
    private Integer writeQueueNum;

    /**
     * 读队列数
     */
    private Integer readQueueNum;

    /**
     * 权限
     */
    private Integer perm;

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
     * 版本号
     */
    private Long version;

    /**
     * 是否删除
     */
    private Long deleted;


}
