package com.example.demo.config.support;

import com.example.demo.mapper.MenuMapper;
import com.example.demo.model.Role;
import com.example.demo.pojo.MenuWithRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;


/**
 * 根据请求路径, 获取访问条件
 * 比如:
 * 访问 /user 需要 admin 角色 或者 super_admin 角色
 * 访问 /book 需要 Admin 用户
 * <p>
 * 收集到的条件， 会传入 AccessDecisionManager 中，做判断
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements
        FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws
            IllegalArgumentException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();

        List<MenuWithRole> menuRoleList = menuMapper.getMenusWithRole();
        for (MenuWithRole menuWithRole : menuRoleList) {
            if (antPathMatcher.match(menuWithRole.getUrl(), requestUrl)) {
                List<Role> roleList = menuWithRole.getRoleList();

                String[] roleResult = new String[roleList.size()];
                for (int i = 0; i < roleList.size(); i++) {
                    roleResult[i] = roleList.get(i).getName();
                }
                return SecurityConfig.createList(roleResult);
            }
        }

        // 没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("role_login");
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