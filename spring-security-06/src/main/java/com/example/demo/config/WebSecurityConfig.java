package com.example.demo.config;

import com.example.demo.config.handler.AuthenticationAccessDeniedHandler;
import com.example.demo.config.handler.MyAuthenticationFailureHandler;
import com.example.demo.config.handler.MyAuthenticationSuccessHandler;
import com.example.demo.config.service.MyUserDetailsService;
import com.example.demo.config.support.UrlAccessDecisionManager;
import com.example.demo.config.support.UrlFilterInvocationSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @auther zgp
 * @desc security 配置
 * @date 2020/8/3
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UrlFilterInvocationSecurityMetadataSource
            urlFilterInvocationSecurityMetadataSource;

    @Autowired
    private UrlAccessDecisionManager urlAccessDecisionManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    // 密码加密工具
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置如何获取用户信息
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login.html", "/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable();

        // 配置表单登录
        http.formLogin()
                // username 和 password 可以不用配置
                .usernameParameter("username")
                .passwordParameter("password")
                // 登录失败， 返回错误信息
                .failureHandler(myAuthenticationFailureHandler)
                // 登录成功,返回成功信息
                .successHandler(myAuthenticationSuccessHandler);

        // 配置动态权限
        http.authorizeRequests()
                .withObjectPostProcessor(
                        new ObjectPostProcessor<FilterSecurityInterceptor>() {
                            @Override
                            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                                //  配置访问 url 的条件
                                o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                                // 根据上面的条件, 去选择放行还是拒绝
                                o.setAccessDecisionManager(urlAccessDecisionManager);
                                return o;
                            }
                        })

                .and()
                .logout().permitAll()
                .and()
                // 如果权限不足， 返回对应的错误信息
                .exceptionHandling()
                .accessDeniedHandler(authenticationAccessDeniedHandler);

    }
}



