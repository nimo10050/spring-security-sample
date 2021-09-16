package com.example.security.configuration;

import com.example.security.web.FilterChainProxy;
import com.example.security.web.HttpSecurity;
import com.example.security.web.SecurityFilterChain;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther zgp
 * @desc security 配置
 * @date 2020/8/3
 */
@Configuration
public class WebSecurityAutoConfiguration  {


    @Bean("springSecurityFilterChain")
    public Filter filterChainProxy(ConfigurableListableBeanFactory beanFactory) {

        List<SecurityFilterChain> securityFilterChains = new ArrayList<>();

        // 从 Spring 容器中获取类型为 WebSecurityConfigurationAdapter 的配置类
        List<WebSecurityConfigurationAdapter> webSecurityConfigurers = getWebSecurityConfigurationAdapters(beanFactory);

        // 执行  WebSecurityConfigurationAdapter 的 configure 方法
        for (WebSecurityConfigurationAdapter webSecurityConfigurer : webSecurityConfigurers) {
            HttpSecurity httpSecurity = new HttpSecurity();
            webSecurityConfigurer.configure(httpSecurity);
            SecurityFilterChain securityFilterChain = httpSecurity.build();
            securityFilterChains.add(securityFilterChain);
        }

        return new FilterChainProxy(securityFilterChains);
    }

    private List<WebSecurityConfigurationAdapter> getWebSecurityConfigurationAdapters(ConfigurableListableBeanFactory beanFactory) {
        List<WebSecurityConfigurationAdapter> webSecurityConfigurers = new ArrayList<>();
        Map<String, WebSecurityConfigurationAdapter> beansOfType = beanFactory
                .getBeansOfType(WebSecurityConfigurationAdapter.class);
        for (Map.Entry<String, WebSecurityConfigurationAdapter> entry : beansOfType.entrySet()) {
            webSecurityConfigurers.add(entry.getValue());
        }
        return webSecurityConfigurers;
    }

    @Bean
    @ConditionalOnBean(name = "springSecurityFilterChain")
    public DelegatingFilterProxyRegistrationBean securityFilterChainRegistration() {
        DelegatingFilterProxyRegistrationBean registration = new DelegatingFilterProxyRegistrationBean(
                "springSecurityFilterChain");
        registration.setOrder(-100);
        Set<DispatcherType> dispatcherTypes = new HashSet<>(
                Arrays.asList(DispatcherType.ASYNC, DispatcherType.ERROR, DispatcherType.REQUEST));
        registration.setDispatcherTypes(dispatcherTypes.stream()
                .map((type) -> javax.servlet.DispatcherType.valueOf(type.name()))
                .collect(Collectors.collectingAndThen(Collectors.toSet(), EnumSet::copyOf)));
        return registration;
    }


}


