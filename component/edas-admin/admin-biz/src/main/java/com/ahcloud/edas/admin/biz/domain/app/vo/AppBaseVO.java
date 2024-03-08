package com.ahcloud.edas.admin.biz.domain.app.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 16:08
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppBaseVO {

    /**
     * 主键id
     */
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
     * app名称
     */
    private String appName;

    /**
     * 环境变量
     */
    private String env;

    /**
     * app类型
     */
    private Integer type;

    /**
     * 类型名称
     */
    private String typeName;

}
