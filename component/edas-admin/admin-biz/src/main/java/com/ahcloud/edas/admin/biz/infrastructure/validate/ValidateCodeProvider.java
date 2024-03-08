package com.ahcloud.edas.admin.biz.infrastructure.validate;


import com.ahcloud.edas.admin.biz.domain.code.SendResult;
import com.ahcloud.edas.admin.biz.infrastructure.function.VoidFunction;

import java.util.function.Supplier;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 15:24
 **/
public interface ValidateCodeProvider {

    /**
     * 发送验证码
     * @param sendMode
     * @return
     */
    SendResult<Void> send(SendMode sendMode);

    /**
     * 存储验证码
     * @param sendMode
     * @param code
     * @return
     */
    boolean save(SendMode sendMode, String code);

    /**
     * 校验验证码
     * @param sendMode
     * @param sourceCode
     * @return
     */
    void validate(SendMode sendMode, String sourceCode);

    /**
     * 校验验证码
     * @param sendMode
     * @param sourceCode
     * @param function
     * @return
     */
    void validate(SendMode sendMode, String sourceCode, VoidFunction function);

    /**
     * 校验验证码
     * @param sendMode
     * @param sourceCode
     * @param supplier
     * @return
     */
    <R> void validate(SendMode sendMode, String sourceCode, Supplier<R> supplier);

    /**
     * 获取验证码
     * @param sendMode
     * @return
     */
    String get(SendMode sendMode);

    /**
     * 删除验证码
     * @param sendMode
     * @return
     */
    boolean delete(SendMode sendMode);

    /**
     * 是否支持
     * @param sendMode
     * @return
     */
    boolean support(SendMode sendMode);
}
