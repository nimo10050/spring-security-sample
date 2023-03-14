//package com.example.demo.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.stereotype.Component;
//
///**
// * @auther zgp
// * @desc
// * @date 2021/1/1
// */
//@EnableAuthorizationServer
//@Component
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
////    @Override
////    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
////        security.
////    }
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory().withClient("app").secret(passwordEncoder.encode("app")).authorizedGrantTypes("password").scopes("all");
//
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager);
//    }
//}
