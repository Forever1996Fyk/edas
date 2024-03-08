package com.ahcloud.edas.admin.biz.domain.app.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 16:07
 **/
@Data
public class AppBaseQuery extends PageQuery {

    /**
     * appCode
     */
    private String appCode;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * appName
     */
    private String appName;

    /**
     * env
     */
    private String env;
}
