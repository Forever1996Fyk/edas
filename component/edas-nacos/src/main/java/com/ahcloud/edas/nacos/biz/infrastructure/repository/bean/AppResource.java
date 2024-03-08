package com.ahcloud.edas.nacos.biz.infrastructure.repository.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * app资源表
 * </p>
 *
 * @author auto_generation
 * @since 2023-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("app_resource")
public class AppResource implements Serializable {

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
     * 资源类型
     */
    private Integer type;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源配置
     */
    private String resourceConfig;

    /**
     * 资源地址
     */
    private String url;

    /**
     * 资源地址掩码
     */
    private String urlMask;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 端口号掩码
     */
    private String portMask;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户名掩码
     */
    private String usernameMask;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码掩码
     */
    private String passwordMask;

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
