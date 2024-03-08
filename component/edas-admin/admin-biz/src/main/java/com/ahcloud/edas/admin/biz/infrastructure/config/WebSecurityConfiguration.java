package com.ahcloud.edas.admin.biz.infrastructure.config;

import com.ahcloud.edas.admin.biz.infrastructure.security.provider.ThirdAuthenticationProvider;
import com.ahcloud.edas.admin.biz.infrastructure.security.provider.ValidateCodeAuthenticationProvider;
import com.ahcloud.edas.uaa.starter.configuration.EdasSecurityConfiguration;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Order(101)
public class WebSecurityConfiguration extends EdasSecurityConfiguration {

    /**
     * 默认的账号密码登录校验
     */
    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 密码校验，从数据库中校验密码
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.authenticationProvider(validateCodeAuthenticationProvider());
        auth.authenticationProvider(thirdAuthenticationProvider());
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 使用 BCryptPasswordEncoder 进行密码加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 重新注入DaoAuthenticationProvider
     *
     * 设置setHideUserNotFoundExceptions为false, 可以抛出UsernameNotFoundException
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    /**
     * 重新注入DaoAuthenticationProvider
     *
     * 设置setHideUserNotFoundExceptions为false, 可以抛出UsernameNotFoundException
     * @return
     */
    @Bean
    public ValidateCodeAuthenticationProvider validateCodeAuthenticationProvider() {
        ValidateCodeAuthenticationProvider validateCodeAuthenticationProvider = new ValidateCodeAuthenticationProvider();
        validateCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
        return validateCodeAuthenticationProvider;
    }

    /**
     * 注入ThirdAuthenticationProvider
     *
     * 设置setHideUserNotFoundExceptions为false, 可以抛出UsernameNotFoundException
     * @return
     */
    @Bean
    public ThirdAuthenticationProvider thirdAuthenticationProvider() {
        ThirdAuthenticationProvider thirdAuthenticationProvider = new ThirdAuthenticationProvider();
        thirdAuthenticationProvider.setUserDetailsService(userDetailsService);
        return thirdAuthenticationProvider;
    }
}
