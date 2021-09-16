package com.example.security.web;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @auther zgp
 * @desc
 * @date 2021/9/16
 */
public class DefaultSecurityFilterChain implements SecurityFilterChain{

    private List<Filter> filters;

    private String urlPath;

    public DefaultSecurityFilterChain(String urlPath, List<Filter> filters) {
        this.urlPath = urlPath;
        this.filters = filters;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        return urlPath.equals(servletPath);
    }

    @Override
    public List<Filter> getFilters() {
        return this.filters;
    }
}
