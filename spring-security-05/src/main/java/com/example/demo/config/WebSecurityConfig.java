package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @auther zgp
 * @desc security 配置
 * @date 2020/8/3
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UrlFilterInvocationSecurityMetadataSource
            urlFilterInvocationSecurityMetadataSource;

    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;

    @Autowired
    AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("xiaoming").password(passwordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(
                        new ObjectPostProcessor<FilterSecurityInterceptor>() {
                            @Override
                            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                                o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                                o.setAccessDecisionManager(urlAccessDecisionManager);
                                return o;
                            }
                        })

                .and()
                .formLogin()
//                    .loginPage("/login_p")
//                    .loginProcessingUrl("/login")
//                    .usernameParameter("username")
//                    .passwordParameter("password")
                .permitAll()
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest
                                                                httpServletRequest, HttpServletResponse httpServletResponse,
                                                        AuthenticationException e) throws
                            IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        StringBuffer sb = new StringBuffer();
                        sb.append("{\"status\":\"error\",\"msg\":\"");
                        if (e instanceof UsernameNotFoundException || e instanceof
                                BadCredentialsException) {
                            sb.append("用户名或密码输入错误，登录失败!");
                        } else if (e instanceof DisabledException) {
                            sb.append("账户被禁用，登录失败，请联系管理员!");
                        } else {
                            sb.append("登录失败!");
                        }
                        sb.append("\"}");
                        out.write(sb.toString());
                        out.flush();
                        out.close();
                    }
                })
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(
                            HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse,
                            Authentication authentication)
                            throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        ObjectMapper objectMapper = new ObjectMapper();
                        String s = "{\"status\":\"success\",\"msg\":\"登录成功\"}";
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(authenticationAccessDeniedHandler);
    }
}



