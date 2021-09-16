package com.example.security.web;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @auther zgp
 * @desc
 * @date 2021/9/16
 */
public class FilterChainProxy extends GenericFilterBean {

    private List<SecurityFilterChain> securityFilterChains;

    public FilterChainProxy(List<SecurityFilterChain> securityFilterChains) {
        this.securityFilterChains = securityFilterChains;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        List<Filter> filters = getFilters(servletRequest);
        if (filters == null || filters.size() == 0) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        VirtualFilterChain vfc = new VirtualFilterChain(filterChain, filters);
        vfc.doFilter(servletRequest, servletResponse);

    }

    private List<Filter> getFilters(ServletRequest servletRequest) {
        for (SecurityFilterChain securityFilterChain : securityFilterChains) {
            if (securityFilterChain.matches((HttpServletRequest) servletRequest)) {
                return securityFilterChain.getFilters();
            }
        }
        return null;
    }

    private static class VirtualFilterChain implements FilterChain {

        private final FilterChain originalChain;
        private final List<Filter> additionalFilters;

        private int currentPosition = 0;

        private VirtualFilterChain(FilterChain chain, List<Filter> additionalFilters) {
            this.originalChain = chain;
            this.additionalFilters = additionalFilters;
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse)
                throws IOException, ServletException {

            if (currentPosition == additionalFilters.size()) {
                originalChain.doFilter(servletRequest, servletResponse);
            } else {
                currentPosition ++;
                Filter filter = additionalFilters.get(currentPosition-1);
                filter.doFilter(servletRequest, servletResponse, this);

            }
        }
    }

}
