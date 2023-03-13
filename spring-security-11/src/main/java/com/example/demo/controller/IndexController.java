package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/hello")
    public Object sayHello(){
        return "hello world";
    }
    
    @GetMapping("/getUser")
    @PreAuthorize("hasRole('ADMIN')")
    public void getUser() {
    	System.out.println("getUser");
    }


}
