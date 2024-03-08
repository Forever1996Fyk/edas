package com.ahcloud.edas.admin.biz.domain.dict.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 17:00
 **/
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SysDictDetailVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典详情编码
     */
    private String dictDetailCode;

    /**
     * 字典详情名称
     */
    private String dictDetailDesc;

    /**
     * 排序
     */
    private Integer detailOrder;

    /**
     * 是否可编辑
     */
    private Integer edit;

    /**
     * 是否可编辑
     */
    private String editName;

    /**
     * 备注
     */
    private String remark;

}
