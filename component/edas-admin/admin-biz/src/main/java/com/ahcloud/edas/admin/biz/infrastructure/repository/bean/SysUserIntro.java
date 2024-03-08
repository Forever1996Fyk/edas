package com.ahcloud.edas.admin.biz.infrastructure.repository.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统用户简介表
 * </p>
 *
 * @author auto_generation
 * @since 2022-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_intro")
public class SysUserIntro implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 标签
     */
    private String tags;

    /**
     * 位置
     */
    private String location;

    /**
     * 生日
     */
    private Date birthDay;

    /**
     * 技能
     */
    private String skills;

    /**
     * 爱好
     */
    private String hobby;

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
