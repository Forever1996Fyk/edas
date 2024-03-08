package com.ahcloud.edas.admin.biz.domain.user.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-04 17:46
 **/
@Data
public class SysUserQuery extends PageQuery {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门编码
     */
    private String deptCode;
}
