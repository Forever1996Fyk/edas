package com.ahcloud.edas.admin.biz.domain.dict.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 16:16
 **/
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SysDictVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 类型:系统/业务
     */
    private Integer type;

    /**
     * 是否可变
     */
    private Integer changed;

    /**
     * 是否可变
     */
    private String changeName;
}
