package com.ahcloud.edas.admin.biz.domain.dict.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 17:36
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SelectDictLabelVo {

    /**
     * 标签id
     */
    private Long labelId;

    /**
     * 标签编码
     */
    private String labelCode;

    /**
     * 标签名称
     */
    private String labelName;
}
