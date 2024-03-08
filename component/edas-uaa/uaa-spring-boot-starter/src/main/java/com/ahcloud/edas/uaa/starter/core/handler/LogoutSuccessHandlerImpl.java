package com.ahcloud.edas.uaa.starter.core.handler;

import com.ahcloud.edas.uaa.starter.core.constant.enums.UaaRetCodeEnum;
import com.ahcloud.edas.uaa.starter.core.service.WebSecurityTokenService;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 * 
 * @author YuKai Fan
 */
@Slf4j
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Resource
    private WebSecurityTokenService tokenService;

    /**
     * 退出处理
     * 
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String token = tokenService.getToken(request);
        if (StringUtils.isNotBlank(token)) {
            // 删除用户缓存记录
            tokenService.deleteEdasUser(token);
        }
	    response.setStatus(UaaRetCodeEnum.LOGOUT_SUCCESS.getCode());
	    response.setContentType("application/json");
	    response.setCharacterEncoding("utf-8");
	    response.getWriter().print(JsonUtils.toJsonString(ResponseResult.ofSuccess()));
    }
}
