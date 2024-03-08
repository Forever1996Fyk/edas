package com.ahcloud.edas.admin.biz.domain.menu.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-15 10:06
 **/
@Data
public class SelectMenuApiQuery extends PageQuery {

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 接口编码
     */
    private String apiCode;
}
