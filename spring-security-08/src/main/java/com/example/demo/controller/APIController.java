package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {

    @GetMapping("/api/user")
    public Object user(){
        return "api user";
    }

    @GetMapping("/oauth/user")
    public Object sayHello1(){
        return "oauth user";
    }


    @GetMapping("/user/user2")
    public Object sayHello2(){
        return "user list2";
    }


    @GetMapping("/roles")
    public Object sayHello3(){
        return "role list222";
    }


    @GetMapping("/user/user3")
    public Object sayHello4(){
        return "user 333";
    }


    @GetMapping("/test/user3")
    public Object sayHello5(){
        return "user 333";
    }
}
