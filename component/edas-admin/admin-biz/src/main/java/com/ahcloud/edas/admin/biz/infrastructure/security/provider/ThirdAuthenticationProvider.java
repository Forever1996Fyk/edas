package com.ahcloud.edas.admin.biz.infrastructure.security.provider;

import com.ahcloud.edas.admin.biz.domain.user.third.ThirdUserInfo;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ErrorCodeEnum;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ThirdSourceEnum;
import com.ahcloud.edas.admin.biz.infrastructure.security.third.ThirdAuthService;
import com.ahcloud.edas.admin.biz.infrastructure.security.third.ThirdAuthServiceFactory;
import com.ahcloud.edas.admin.biz.infrastructure.security.token.ThirdAuthenticationToken;
import com.ahcloud.edas.admin.biz.infrastructure.security.token.ValidateCodeAuthenticationToken;
import com.ahcloud.edas.uaa.starter.core.exception.SecurityErrorException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/2 16:48
 **/
@Getter
public class ThirdAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ThirdAuthenticationToken thirdAuthenticationToken = (ThirdAuthenticationToken) authentication;
        String loginTmpCode = (String) thirdAuthenticationToken.getPrincipal();
        Integer source = (Integer) thirdAuthenticationToken.getCredentials();
        ThirdSourceEnum sourceEnum = ThirdSourceEnum.getBySource(source);
        ThirdAuthService thirdAuthService = ThirdAuthServiceFactory.getService(sourceEnum);
        ThirdUserInfo thirdUserInfo = thirdAuthService.getThirdUserInfoByCode(loginTmpCode);
        String mobile = thirdUserInfo.getMobile();
        if (StringUtils.isBlank(mobile)) {
            throw new SecurityErrorException(ErrorCodeEnum.THIRD_ACCOUNT_AUTH_FAILED);
        }
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(thirdUserInfo.getMobile());
        return createSuccessAuthentication(userDetails, authentication, userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ThirdAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    private Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                       UserDetails user) {
        ValidateCodeAuthenticationToken result = new ValidateCodeAuthenticationToken(principal,
                authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

}
