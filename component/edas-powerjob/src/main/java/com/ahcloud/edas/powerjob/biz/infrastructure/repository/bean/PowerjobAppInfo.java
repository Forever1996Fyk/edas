package com.ahcloud.edas.powerjob.biz.infrastructure.repository.bean;

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
 * powerjob app信息
 * </p>
 *
 * @author auto_generation
 * @since 2024-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("powerjob_app_info")
public class PowerjobAppInfo implements Serializable {

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
     * powerjobAppId
     */
    private Long powerjobAppId;

    /**
     * powerjobAppName
     */
    private String powerjobAppName;

    /**
     * powerjobAppPassword
     */
    private String powerjobAppPassword;

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
    private Long version;

    /**
     * 拓展字段
     */
    private String extension;

    /**
     * 是否删除
     */
    private Long deleted;


}
