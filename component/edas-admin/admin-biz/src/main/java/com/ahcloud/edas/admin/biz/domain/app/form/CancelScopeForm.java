package com.ahcloud.edas.admin.biz.domain.app.form;

import lombok.Data;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 16:26
 **/
@Data
public class CancelScopeForm {

    /**
     * appId
     */
    private List<Long> idList;
}
