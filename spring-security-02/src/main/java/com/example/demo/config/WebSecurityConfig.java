package com.example.demo.config;

import com.example.demo.config.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();// 必须有, 不然会 403 forbidden

        // 访问 "/form/login", "/loginPage.html"   放行
        http.antMatcher("/api/**").authorizeRequests().antMatchers("/hello").authenticated();
        //http.httpBasic();
        //http.formLogin();
        http.addFilterAt(new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

            }
        }, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 配置 用户登录处理类
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* 将用户信息存储到内存中
           实际上不会这样做，了解下即可
        auth.inMemoryAuthentication()
                .withUser("zhangsan")
                .password(passwordEncoder().encode("123456"))
                .authorities("admin");*/

        auth.userDetailsService(userDetailsService());
    }

    /**
     * 自定义登录处理
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }


    /**
     * 加密工具
     * 2.x 版本的 spring-security-starter 必须加上
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
