package com.future.clockio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String printHello(){
        return "Hello World!";
    }

    @GetMapping("/about")
    public String printAbout() {return "This is an about page!"; }

    @GetMapping("/api/x")
    public String apix() {return "You are here! after Bearer Authorization!"; }
}
