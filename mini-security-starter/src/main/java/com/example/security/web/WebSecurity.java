package com.example.security.web;

import com.example.security.configuration.WebSecurityConfigurationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther zgp
 * @desc
 * @date 2021/9/23
 */
public class WebSecurity {

    private List<SecurityFilterChain> securityFilterChains = new ArrayList<>();

    public void apply(WebSecurityConfigurationAdapter webSecurityConfigurer) {
        HttpSecurity httpSecurity = new HttpSecurity();
        webSecurityConfigurer.configure(httpSecurity);
        SecurityFilterChain securityFilterChain = httpSecurity.build();
        securityFilterChains.add(securityFilterChain);
    }

    public FilterChainProxy build() {
        return new FilterChainProxy(securityFilterChains);
    }

}
