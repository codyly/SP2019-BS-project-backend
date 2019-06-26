package com.cody.learning;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@EnableAutoConfiguration
@ComponentScan
@ComponentScan("com.cody.learning.util")
@ComponentScan("com.cody.learning.components")
@ComponentScan("com.cody.learning.dbmanager")

public class LearningApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningApplication.class, args);
    }

}
