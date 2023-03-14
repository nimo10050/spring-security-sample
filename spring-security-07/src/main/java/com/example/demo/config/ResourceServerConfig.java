package com.example.demo.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * @auther zgp
 * @desc
 * @date 2021/1/1
 */
@EnableResourceServer
@Component
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/oauth/**").permitAll();
//        http.authorizeRequests().anyRequest().authenticated();
        // request.RequestMatcherConfigurer.antMatchers().chainRequestMatchers().requestMatcher
        // http.requestMatcher(new OrRequestMatcher(this.matchers));
        http.requestMatchers()
                .antMatchers("/user/**")
                .and()
                .authorizeRequests()
                .antMatchers("/user/user1/**").permitAll()
                .antMatchers("/user/user2/**").authenticated()
        //.anyRequest().authenticated()
        ;

        // http.requestMatchers().antMatchers("").and().authorizeRequests()
        // http.requestMatcher(new AntPathRequestMatcher(antPattern));
        http.antMatcher("/user/**")
                .authorizeRequests()
                .antMatchers("/user/user1/**").permitAll()
                .antMatchers("/user/user2/**").authenticated()
        .anyRequest().authenticated();


        // http.authorizeRequests().anyRequest().authenticated();


    }
}
