package com.ahcloud.edas.uaa.starter.configuration;

import com.ahcloud.edas.uaa.starter.core.filter.RedisAuthenticationTokenFilter;
import com.ahcloud.edas.uaa.starter.core.handler.AccessDeniedHandlerImpl;
import com.ahcloud.edas.uaa.starter.core.handler.AuthenticationEntryPointImpl;
import com.ahcloud.edas.uaa.starter.core.handler.LogoutSuccessHandlerImpl;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description: Security配置类
 * 查看其注解源码，主要是引用WebSecurityConfiguration.class 和 加入了@EnableGlobalAuthentication 注解 ，这里就不介绍了，我们只要明白添加 @EnableWebSecurity 注解将开启 Security 功能。
 * @author: YuKai Fan
 * @create: 2021-12-17 15:22
 **/
@Configuration
@EnableWebSecurity
public class EdasSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * 退出处理类
     */
    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * 认证失败处理类
     */
    @Resource
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 无权限处理类
     */
    @Resource
    private AccessDeniedHandlerImpl accessDeniedHandler;

    /**
     * token 验证过滤器
     */
    @Resource
    private RedisAuthenticationTokenFilter authenticationTokenFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors().and()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                // 调整为让 Spring Security 不创建和使用 session, 因为前后端分离项目, 使用token验证方式, 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 可认证访问接口
                .anyRequest()
                // 自定义接口权限认证
                .access("@accessExecutor.access(request, authentication)")
                .and()
                .headers().frameOptions().disable();

        // 配置csrf
//        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.csrf().disable();

        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);

        // 添加Redis filter
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationEntryPointFailureHandler(unauthorizedHandler);
    }
}
