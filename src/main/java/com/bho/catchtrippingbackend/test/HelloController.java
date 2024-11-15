package com.bho.catchtrippingbackend.test;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }
}