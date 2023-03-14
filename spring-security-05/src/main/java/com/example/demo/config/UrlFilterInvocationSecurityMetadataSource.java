package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

@Component
public class UrlFilterInvocationSecurityMetadataSource implements
        FilterInvocationSecurityMetadataSource {

    /** 模拟一些数据
     * 登录接口，任意用户可以访问
     * 用户列表接口，只有 admin 用户可以访问
     * **/

    // 登录 url
    private static final String LOGIN_URL = "/login";

    // 用户管理
    private static final String USER_LIST = "/user";

    // 管理员角色
    private static final String ADMIN = "ROLE_ADMIN";

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws
            IllegalArgumentException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        if (LOGIN_URL.equals(requestUrl)) {
            return null;
        }

        // 如果当前请求url 是 /user, 那么管理员角色才可以访问
        if (antPathMatcher.match(USER_LIST, requestUrl)) {
            return SecurityConfig.createList(ADMIN);
        }

        //没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}