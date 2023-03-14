package com.example.demo.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {



    @PostMapping("/hello")
    public Object sayHello(){

        return "hello world";
    }


}
