package com.project.my_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class HelloController {
    @GetMapping("/ola")
    public String dizerOla() {
        return "Minha primeira API RESTful está no ar!";
    }
}