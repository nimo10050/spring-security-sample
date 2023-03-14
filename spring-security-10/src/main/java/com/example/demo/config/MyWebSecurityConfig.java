package com.example.demo.config;

import com.example.security.configuration.WebSecurityConfigurationAdapter;
import com.example.security.web.builder.HttpSecurity;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zgp
 * @desc
 * @date 2021/9/16
 */
@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurationAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) {
        httpSecurity.antMatches("/hello");
        httpSecurity.formLogin();
        httpSecurity.basic();
    }

}
