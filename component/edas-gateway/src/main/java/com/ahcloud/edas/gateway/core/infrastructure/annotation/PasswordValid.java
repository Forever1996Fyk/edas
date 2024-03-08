package com.ahcloud.edas.gateway.core.infrastructure.annotation;




import com.ahcloud.edas.gateway.core.infrastructure.validator.PasswordValidator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-01-23 22:21
 **/
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValid {

    String message() default "密码输入格式错误";

    Class[] groups() default {};

    Class[] payload() default {};
}
