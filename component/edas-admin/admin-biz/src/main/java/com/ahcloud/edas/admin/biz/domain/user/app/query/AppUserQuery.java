package com.ahcloud.edas.admin.biz.domain.user.app.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/26 14:56
 **/
@Data
public class AppUserQuery extends PageQuery {

    /**
     * 用户名称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

}
