package com.example.security.filter;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @auther zgp
 * @desc
 * @date 2021/9/16
 */
public class BasicAuthenticationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader("Authorization");
        boolean success = authorization != null;
        if (success) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println("basic 认证信息没有传！");
            return;
        }

    }
}
