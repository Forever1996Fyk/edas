package com.ahcloud.edas.admin.biz.infrastructure.security.provider;


import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ErrorCodeEnum;
import com.ahcloud.edas.uaa.starter.core.exception.SecurityErrorException;
import com.ahcloud.edas.admin.biz.infrastructure.security.token.ValidateCodeAuthenticationToken;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-05 22:48
 **/
@Getter
public class ValidateCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ValidateCodeAuthenticationToken authenticationToken = (ValidateCodeAuthenticationToken) authentication;
        String sender = (String) authenticationToken.getPrincipal();
        UserDetails userDetails = this.getUserDetailsService().loadUserByUsername(sender);
        if (Objects.isNull(userDetails)) {
            throw new SecurityErrorException(ErrorCodeEnum.VALIDATE_CODE_USER_IS_NULL);
        }
        return createSuccessAuthentication(userDetails, authentication, userDetails);
    }

    private Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        ValidateCodeAuthenticationToken result = new ValidateCodeAuthenticationToken(principal,
                authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ValidateCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
