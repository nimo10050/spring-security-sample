package com.example.security.web;

import com.example.security.filter.BasicAuthenticationFilter;
import com.example.security.filter.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther zgp
 * @desc
 * @date 2021/9/16
 */
public class HttpSecurity {

    List<Filter> filters = new ArrayList<>();
    private String url = "/**";
    public void antMatches(String pattern) {
        this.url = pattern;
    }

    public void formLogin() {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filters.add(filter);
    }

    public void basic() {
        BasicAuthenticationFilter filter = new BasicAuthenticationFilter();
        filters.add(filter);
    }

    public SecurityFilterChain build() {
        return new DefaultSecurityFilterChain(url, filters);
    }

}
