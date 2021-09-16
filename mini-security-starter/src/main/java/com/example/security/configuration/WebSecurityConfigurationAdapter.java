package com.example.security.configuration;

import com.example.security.web.HttpSecurity;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zgp
 * @desc
 * @date 2021/9/16
 */
@Configuration
public class WebSecurityConfigurationAdapter {

    public void configure(HttpSecurity httpSecurity) {
        httpSecurity.antMatches("/hello");
        httpSecurity.formLogin();
        httpSecurity.basic();
    }
}
