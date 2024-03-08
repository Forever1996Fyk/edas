package com.ahcloud.edas.pulsar.core.application.manager;

import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.pulsar.core.application.service.PulsarRoleTokenService;
import com.ahcloud.edas.pulsar.core.domain.auth.form.RoleTokenAddForm;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.enums.PulsarRetCodeEnum;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.jwt.JwtService;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarRoleToken;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/20 14:55
 **/
@Slf4j
@Component
public class AuthManager {
    @Resource
    private JwtService jwtService;
    @Resource
    private PulsarRoleTokenService pulsarRoleTokenService;

    /**
     * 初始化角色token
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void initRoleToke(RoleTokenAddForm form) {
        PulsarRoleToken existedPulsarRoleToken = pulsarRoleTokenService.getOne(
                new QueryWrapper<PulsarRoleToken>().lambda()
                        .eq(PulsarRoleToken::getAppId, form.getAppId())
                        .eq(PulsarRoleToken::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(existedPulsarRoleToken)) {
            return;
        }
        PulsarRoleToken pulsarRoleToken = new PulsarRoleToken();
        pulsarRoleToken.setAppId(form.getAppId());
        pulsarRoleToken.setAppCode(form.getAppCode());
        pulsarRoleToken.setEnv(form.getEnv());
        pulsarRoleToken.setRole(form.getAppCode());
        String brokerToken = jwtService.createBrokerToken(pulsarRoleToken.getRole(), null);
        if (StringUtils.isBlank(brokerToken)) {
            throw new BizException(PulsarRetCodeEnum.GENERATE_TOKEN_FAILED);
        }
        String userNameBySession = UserUtils.getUserNameBySession();
        pulsarRoleToken.setCreator(userNameBySession);
        pulsarRoleToken.setModifier(userNameBySession);
        pulsarRoleToken.setToken(brokerToken);
        pulsarRoleTokenService.save(pulsarRoleToken);
    }

    /**
     * 获取角色token
     * @param appId
     * @return
     */
    public String getToken(Long appId) {
        PulsarRoleToken existedPulsarRoleToken = pulsarRoleTokenService.getOne(
                new QueryWrapper<PulsarRoleToken>().lambda()
                        .eq(PulsarRoleToken::getAppId, appId)
                        .eq(PulsarRoleToken::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedPulsarRoleToken)) {
            return null;
        }
        return existedPulsarRoleToken.getToken();
    }

    /**
     * 获取角色
     * @param appId
     * @return
     */
    public String getRole(Long appId) {
        PulsarRoleToken existedPulsarRoleToken = pulsarRoleTokenService.getOne(
                new QueryWrapper<PulsarRoleToken>().lambda()
                        .eq(PulsarRoleToken::getAppId, appId)
                        .eq(PulsarRoleToken::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedPulsarRoleToken)) {
            return null;
        }
        return existedPulsarRoleToken.getRole();
    }


    /**
     * 获取角色
     * @param appId
     * @return
     */
    public PulsarRoleToken getRoleToken(Long appId) {
        PulsarRoleToken existedPulsarRoleToken = pulsarRoleTokenService.getOne(
                new QueryWrapper<PulsarRoleToken>().lambda()
                        .eq(PulsarRoleToken::getAppId, appId)
                        .eq(PulsarRoleToken::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedPulsarRoleToken)) {
            return null;
        }
        return existedPulsarRoleToken;
    }

}
