package com.ahcloud.edas.pulsar.core.infrastructure.repository.bean;

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
 * pulsar角色token管理
 * </p>
 *
 * @author auto_generation
 * @since 2024-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pulsar_role_token")
public class PulsarRoleToken implements Serializable {

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
     * 角色
     */
    private String role;

    /**
     * 认证token
     */
    private String token;

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
