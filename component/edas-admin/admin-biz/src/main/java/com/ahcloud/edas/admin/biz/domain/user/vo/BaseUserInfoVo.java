package com.ahcloud.edas.admin.biz.domain.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-21 13:33
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserInfoVo {

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 性别名称
     */
    private String sexName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 岗位名称
     */
    private String postName;

}
