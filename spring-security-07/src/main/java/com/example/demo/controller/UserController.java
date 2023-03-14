package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    public Object sayHello(){
        return "user list";
    }

    @GetMapping("/user/user1")
    public Object sayHello1(){
        return "user list1";
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
