package com.ahcloud.edas.devops.config.biz.infrastructure.repository.bean;

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
 * app配置表
 * </p>
 *
 * @author auto_generation
 * @since 2023-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("devops_config")
public class DevopsConfig implements Serializable {

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
     * 环境变量
     */
    private String env;

    /**
     * devops类型
     * 1: Java, 2: Node, 3: html
     */
    private Integer type;

    /**
     * 部署配置
     */
    private String developmentConfig;

    /**
     * gitlab配置
     */
    private String gitlabConfig;

    /**
     * jenkins配置
     */
    private String jenkinsConfig;

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
     * 行版本号
     */
    @Version
    private Integer version;

    /**
     * 拓展字段
     */
    private String extension;

    /**
     * 是否删除
     */
    private Long deleted;


}
