package com.ahcloud.edas.admin.biz.domain.role.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-15 10:06
 **/
@Data
public class SelectRoleApiQuery extends PageQuery {

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 接口编码
     */
    private String apiCode;
}
