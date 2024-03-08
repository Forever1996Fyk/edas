package com.ahcloud.edas.admin.biz.application.manager;

import com.ahcloud.edas.admin.biz.domain.code.SendResult;
import com.ahcloud.edas.admin.biz.infrastructure.function.VoidFunction;
import com.ahcloud.edas.admin.biz.infrastructure.validate.SendMode;
import com.ahcloud.edas.admin.biz.infrastructure.validate.ValidateCodeProvider;
import com.ahcloud.edas.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.log.LogMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @program: permissions-center
 * @description: 验证码管理器
 * @author: YuKai Fan
 * @create: 2022-01-09 16:48
 **/
@Slf4j
@Component
public class ValidateCodeManager implements InitializingBean {

    @Resource
    private List<ValidateCodeProvider> providers;

    /**
     * 发送验证码
     * @param sendMode
     * @return
     */
    public SendResult<Void> sendCode(SendMode sendMode) {
        SendResult<Void> sendResult = null;
        int currentPosition = 0;
        int size = this.providers.size();
        for (ValidateCodeProvider provider : providers) {
            if (!provider.support(sendMode)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("ValidateCodeProvider request with %s (%d/%d)",
                        provider.getClass().getSimpleName(), ++currentPosition, size)));
            }

            sendResult = provider.send(sendMode);
            if (!Objects.isNull(sendResult)) {
                break;
            }
        }
        return sendResult;
    }

    /**
     * 校验验证码
     *
     * @param sendMode
     * @param code
     * @return
     */
    public void validateCode(SendMode sendMode, String code) {
        int currentPosition = 0;
        int size = this.providers.size();
        for (ValidateCodeProvider provider : providers) {
            if (!provider.support(sendMode)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("ValidateCodeProvider request with %s (%d/%d)",
                        provider.getClass().getSimpleName(), ++currentPosition, size)));
            }

            provider.validate(sendMode, code);
        }
    }

    /**
     * 校验验证码
     *
     * @param sendMode
     * @param code
     * @return
     */
    public void validateCode(SendMode sendMode, String code, VoidFunction function) {
        int currentPosition = 0;
        int size = this.providers.size();
        for (ValidateCodeProvider provider : providers) {
            if (!provider.support(sendMode)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("ValidateCodeProvider request with %s (%d/%d)",
                        provider.getClass().getSimpleName(), ++currentPosition, size)));
            }

            provider.validate(sendMode, code, function);
        }
    }

    /**
     * 校验验证码
     *
     * @param sendMode
     * @param code
     * @return
     */
    public <R> void validateCode(SendMode sendMode, String code, Supplier<R> supplier) {
        int currentPosition = 0;
        int size = this.providers.size();
        for (ValidateCodeProvider provider : providers) {
            if (!provider.support(sendMode)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("ValidateCodeProvider request with %s (%d/%d)",
                        provider.getClass().getSimpleName(), ++currentPosition, size)));
            }

            provider.validate(sendMode, code, supplier);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(providers)) {
            throw new RuntimeException("加载ValidateCodeProvider失败, 不存在验证码处理管理器");
        }
        log.info("初始化ValidateCodeProvider集合数据:{}", JsonUtils.toJsonString(providers));
    }
}
