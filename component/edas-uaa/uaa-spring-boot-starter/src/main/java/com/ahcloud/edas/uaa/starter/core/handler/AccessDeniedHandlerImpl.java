package com.ahcloud.edas.uaa.starter.core.handler;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.common.enums.ErrorCode;
import com.ahcloud.edas.common.util.JsonUtils;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: permissions-center
 * @description: 权限异常处理类
 * @author: YuKai Fan
 * @create: 2021-12-24 16:40
 **/
@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ErrorCode errorCodeEnum = SecurityExceptionHandler.extractErrorCodeEnum(e);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter()
                .print(JsonUtils.toJsonString(
                        ResponseResult.ofFailed(errorCodeEnum)
                ));
        log.error("[权限端点异常处理] ==> [请求地址为: {}, 异常信息为:{}]", request.getRequestURI(), Throwables.getStackTraceAsString(e));
    }
}
