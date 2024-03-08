package com.ahcloud.edas.admin.biz.domain.app.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 15:48
 **/
@Data
public class AppAddForm {

    /**
     * app编码
     */
    @NotEmpty(message = "app编码不能为空")
    private String appCode;

    /**
     * app名称
     */
    @NotEmpty(message = "app名称不能为空")
    private String appName;

    /**
     * app类型
     */
    @NotNull(message = "app类型不能为空")
    private Integer type;

    /**
     * 环境变量
     */
    @Size(min = 1, max = 5, message = "环境变量不能为空")
    private List<String> envList;

    /**
     * 应用描述
     */
    @NotEmpty(message = "应用描述不能为空")
    private String appDesc;
}
