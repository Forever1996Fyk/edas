package com.ahcloud.edas.gateway.core.infrastructure.validator;


import com.ahcloud.edas.gateway.core.infrastructure.annotation.EmailValid;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @program: permissions-center
 * @description: 邮箱校验
 * @author: YuKai Fan
 * @create: 2021-01-23 22:23
 **/
public class EmailValidator implements ConstraintValidator<EmailValid, String> {
    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        Pattern p = Pattern.compile(EMAIL_REGEX);
        return p.matcher(s).matches();
    }
}
