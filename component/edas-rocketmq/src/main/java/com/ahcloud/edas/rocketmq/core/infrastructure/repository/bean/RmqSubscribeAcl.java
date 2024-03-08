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
 * rocketmq订阅组权限
 * </p>
 *
 * @author auto_generation
 * @since 2023-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("rmq_subscribe_acl")
public class RmqSubscribeAcl implements Serializable {

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
     * brokerName 集合
     */
    private String brokerNames;

    /**
     * 消费者组名称
     */
    private String groupName;

    /**
     * 需订阅的topicId
     */
    private Long topicId;

    /**
     * topic权限
     */
    private String topicPerm;

    /**
     * group权限
     */
    private String groupPerm;

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
     * 版本号
     */
    @Version
    private Long version;

    /**
     * 是否删除
     */
    private Long deleted;


}
