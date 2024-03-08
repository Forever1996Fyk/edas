package com.ahcloud.edas.admin.biz.domain.code.form;

import com.ahcloud.edas.common.annotation.EmailValid;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 21:41
 **/
@Data
public class LoginEmailCodeForm {

    /**
     * 邮箱
     */
    @EmailValid
    private String email;
}
