package com.ahcloud.edas.uaa.starter.core.access;

import com.ahcloud.edas.uaa.starter.core.token.InMemoryAuthenticationToken;
import com.ahcloud.edas.uaa.starter.domain.AuthorityApiDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description: app端访问
 * @author: YuKai Fan
 * @create: 2022-04-07 17:23
 **/
@Slf4j
@Component
public class InMemoryAccessProvider extends AbstractAccessProvider {

    private final static String LOG_MARK = "InMemoryAccessProvider";

    @Override
    protected boolean doAccess(AuthorityApiDTO authorityApiDTO, Authentication authentication) {
        return needAuthentication(authorityApiDTO, authentication);
    }

    @Override
    public boolean support(Authentication authentication) {
        return InMemoryAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }
}
