package com.cody.learning;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController{
    @GetMapping("/hello")
    public String hello(){
        return "hello Spring boot";
    }
}