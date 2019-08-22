package com.example.demo.config;

import com.example.demo.config.filter.JwtAuthenticationTokenFilter;
import com.example.demo.config.handler.GoAccessDeniedHandler;
import com.example.demo.config.handler.GoAuthenticationEntryPoint;
import com.example.demo.config.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private GoAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private GoAuthenticationEntryPoint entryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();// 必须有, 不然会 403 forbidden

        /*http.formLogin()
                .loginPage("/loginPage.html")// 自定义登录页
                .loginProcessingUrl("/form/login")// 自定义登录 action, 名字随便起
                .successHandler(successHandler)// 自定义登录成功处理类
                .failureHandler(failureHandler);// 自定义登录失败处理类*/

        // 访问 "/form/login", "/loginPage.html"   放行
        http.authorizeRequests().antMatchers("/user/userInfo", "/user/login", "/form/login", "/loginPage.html").permitAll()
                .antMatchers("/hello").hasRole("superadmin") // 只有superadmin 角色的用户才能访问
                .anyRequest().authenticated();

        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)// 用户没有访问权限处理器
                .authenticationEntryPoint(entryPoint);// 用户没有登录处理器

        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * jwt 登录过滤器
     *
     * @return
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    /**
     * 配置 用户登录处理类
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
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
