package com.ahcloud.edas.uaa.starter.core.access;

import com.ahcloud.edas.uaa.starter.core.constant.UaaConstants;
import com.ahcloud.edas.uaa.starter.core.constant.enums.UaaRetCodeEnum;
import com.ahcloud.edas.uaa.starter.core.exception.ApiAuthorityErrorException;
import com.ahcloud.edas.uaa.starter.core.loader.ResourceLoader;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.uaa.starter.domain.AuthorityApiDTO;
import com.ahcloud.edas.uaa.starter.domain.EdasUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-07 17:00
 **/
@Slf4j
@Component
public abstract class AbstractAccessProvider implements AccessProvider {
    @Resource
    protected ResourceLoader resourceLoader;

    @Override
    public boolean access(HttpServletRequest request, Authentication authentication) {
        /*
        获取当前请求uri
         */
        String uri = request.getRequestURI();

        /*
        根据uri匹配对应的apiCode
         */
        AuthorityApiDTO authorityApiDTO = resourceLoader.getCacheResourceByUri(uri);
        /*
        如果没有匹配到apiCode则直接返回false
         */
        if (Objects.isNull(authorityApiDTO)) {
            log.error("{}[access] get apiCodeKey by uri is empty, uri is {}", getLogMark(), uri);
            return Boolean.FALSE;
        }
        /*
        校验当前api属性
         */
        checkApi(authorityApiDTO);
        if (authorityApiDTO.getGlobal().isYes()) {
            return true;
        }
        log.info("{}[access] current authentication token doAccess", getLogMark());
        return doAccess(authorityApiDTO, authentication);
    }

    /**
     * 处理访问逻辑
     * @param authorityApiDTO
     * @param authentication
     * @return
     */
    protected abstract boolean doAccess(AuthorityApiDTO authorityApiDTO, Authentication authentication);

    /**
     * 日志标记
     * @return
     */
    protected abstract String getLogMark();

    /**
     * 验证api状态
     *
     * @param authorityApiDTO
     */
    private void checkApi(AuthorityApiDTO authorityApiDTO) {
        if (authorityApiDTO.getApiStatusEnum().isDisabled()) {
            throw new ApiAuthorityErrorException(UaaRetCodeEnum.AUTHORITY_API_DISABLED);
        }

        if (authorityApiDTO.getOpen().isNo()) {
            throw new ApiAuthorityErrorException(UaaRetCodeEnum.AUTHORITY_API_NOT_OPEN);
        }
    }

    protected boolean needAuthentication(AuthorityApiDTO authorityApiDTO, Authentication authentication) {
        EdasUser localUser = (EdasUser) authentication.getPrincipal();
        if (Objects.nonNull(localUser) && Objects.nonNull(localUser.getUserInfo())) {
            Long userId = localUser.getUserInfo().getUserId();
            if (Objects.equals(userId, UaaConstants.SUPER_ADMIN)) {
                return true;
            }
        }
        Collection<? extends GrantedAuthority> authorities = localUser.getAuthorities();
        /*
        判断当前用户是否存在该apiCode
         */
        if (!checkUserHasAuthorities(authorities, authorityApiDTO.getApiCode())) {
            log.warn("AccessManager[access] currentUser has no permissions access localUser={}, authorityApiDTO={}",
                    JsonUtils.toJsonString(localUser),
                    JsonUtils.toJsonString(authorityApiDTO)
            );
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 校验用户是否存在权限
     *
     * @param authorities
     * @param apiCode
     * @return
     */
    private boolean checkUserHasAuthorities(Collection<? extends GrantedAuthority> authorities, String apiCode) {
        return authorities.stream()
                .anyMatch(item -> StringUtils.equals(item.getAuthority(), apiCode));
    }
}
