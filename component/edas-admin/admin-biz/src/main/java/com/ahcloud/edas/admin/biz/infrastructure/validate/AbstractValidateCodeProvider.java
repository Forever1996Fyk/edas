package com.ahcloud.edas.admin.biz.infrastructure.validate;

import cn.hutool.core.util.RandomUtil;
import com.ahcloud.edas.admin.biz.domain.code.SendResult;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ErrorCodeEnum;
import com.ahcloud.edas.admin.biz.infrastructure.function.VoidFunction;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.JsonUtils;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 16:15
 **/
@Slf4j
@Component
public abstract class AbstractValidateCodeProvider implements ValidateCodeProvider {

    @Override
    public SendResult<Void> send(SendMode sendMode) {
        SendResult<Void> sendResult = SendResult.ofSuccess();
        try {
            if (!sendMode.isLegal()) {
                // 校验不合法
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_PHONE_OR_EMAIL_FORMAT_ERROR);
            }

            /*
            生成 随机验证码
             */
            String code = RandomUtil.randomNumbers(sendMode.generateCodeLength());

            /*
            发送验证码操作
             */
            doSend(code, sendMode.getSender());

            /*
            保存验证码
             */
            boolean result = save(sendMode, code);
            if (!result) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_SAVE_ERROR, sendMode.getSender());
            }
        } catch (BizException e) {
            log.error("AbstractValidateCodeProvider[send] 验证码发送失败 BizException:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw e;
        } catch (Exception e) {
            log.error("AbstractValidateCodeProvider[send] 验证码发送失败 Exception:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw new BizException(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return sendResult;
    }

    @Override
    public boolean save(SendMode sendMode, String code) {
        return false;
    }

    @Override
    public void validate(SendMode sendMode, String sourceCode) {
        try {
            String code = get(sendMode);
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(sourceCode)) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_INVALID);
            }

            if (!StringUtils.equals(code, sourceCode)) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_DISCORD);
            }
            delete(sendMode);
        } catch (BizException e) {
            log.error("AbstractValidateCodeProvider[validate] 验证码发送失败 BizException:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw e;
        } catch (Exception e) {
            log.error("AbstractValidateCodeProvider[validate] 验证码发送失败 Exception:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw new BizException(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @Override
    public void validate(SendMode sendMode, String sourceCode, VoidFunction function) {
        try {
            String code = get(sendMode);
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(sourceCode)) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_INVALID);
            }

            if (!StringUtils.equals(code, sourceCode)) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_DISCORD);
            }
            // 可以进行业务处理
            function.apply();

            // 走到这一步基本上就是业务处理成功, 可以删除验证码
            delete(sendMode);
        } catch (BizException e) {
            log.error("AbstractValidateCodeProvider[validate] 验证码发送失败 BizException:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw e;
        } catch (Exception e) {
            log.error("AbstractValidateCodeProvider[validate] 验证码发送失败 Exception:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw new BizException(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @Override
    public <R> void validate(SendMode sendMode, String sourceCode, Supplier<R> supplier) {
        try {
            String code = get(sendMode);
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(sourceCode)) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_INVALID);
            }

            if (!StringUtils.equals(code, sourceCode)) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_DISCORD);
            }
            delete(sendMode);
        } catch (BizException e) {
            log.error("AbstractValidateCodeProvider[validate] 验证码发送失败 BizException:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw e;
        } catch (Exception e) {
            log.error("AbstractValidateCodeProvider[validate] 验证码发送失败 Exception:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw new BizException(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    @Override
    public String get(SendMode sendMode) {
        return "";
    }

    @Override
    public boolean delete(SendMode sendMode) {
        return false;
    }

    /**
     * 执行验证码发送
     * @param code 验证码
     * @param sender 发送方
     * @return
     */
    public abstract void doSend(String code, String sender);

}
