package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.naming.Name;
import javax.servlet.Filter;

@RestController
public class IndexController {

    @Resource
    @Qualifier("springSecurityFilterChain")
    private Filter filter;

    @GetMapping("/hello")
    public Object sayHello(){
        return filter.toString();
    }


}
