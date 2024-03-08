package com.ahcloud.edas.gateway.core.infrastructure.validator;


import com.ahcloud.edas.gateway.core.infrastructure.annotation.PhoneNumberValid;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-01-23 22:23
 **/
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValid, String> {

    /**
     * 手机号正则
     */
    public static final String PHONE_REGEX = "^(1[3-9]\\d{9}$)";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        Pattern p = Pattern.compile(PHONE_REGEX);
        return p.matcher(s).matches();
    }
}
